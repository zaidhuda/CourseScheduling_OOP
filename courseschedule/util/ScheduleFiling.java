package courseschedule.util;

import courseschedule.*;

import javax.swing.*;
import java.io.*;

/**
 * Created by Diaz
 * Package: PACKAGE_NAME
 * Time: 11:02 PM
 * Date: 12/8/13.
 */
public class ScheduleFiling {

	private ScheduleBuilder sb;

	public ScheduleFiling(ScheduleBuilder sb) {
		this.sb = sb;
	}

	public void load() {
		final int COURSE = 0, LECTURER = 1, VENUE = 2, SECTION = 3;

		File file = new File("schedule.txt");

		if (!file.exists())
			file = new File("temp.txt");

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {

			StringBuilder stringBuilder;
			String fileLine = br.readLine();
			int type = -1;

			while (fileLine != null) {

				stringBuilder = new StringBuilder(fileLine);
				String newLine = stringBuilder.toString();
				String[] splits = newLine.split(";");

				if (splits.length > 1)
					lineReader(type, splits);

				if ("courses".equalsIgnoreCase(newLine))
					type = COURSE;
				else
					if ("lecturers".equalsIgnoreCase(newLine))
						type = LECTURER;
					else
						if ("venues".equalsIgnoreCase(newLine))
							type = VENUE;
						else
							if ("sections".equalsIgnoreCase(newLine))
								type = SECTION;

				fileLine = br.readLine();
			}

			System.out.println("Loaded Successfully");
		} catch (Exception e) {
			System.out.println("Load Failed: " + e);
		}
	}

	private void lineReader(int type, String[] splits) {

		final int COURSE = 0, LECTURER = 1, VENUE = 2, SECTION = 3;

		switch (type) {
			case COURSE: {

				Course course = new Course(splits[0], splits[1], Integer.parseInt(splits[2]), Integer.parseInt(splits[3]));
				sb.add(course);
				break;

			}
			case LECTURER: { // TODO try combine with venue below

				boolean[][] bool = new boolean[2][6];
				String[] c = splits[1].replaceAll("[\\[\\]]", "").split(", ");
				String[] b = splits[2].replaceAll("[\\[\\]]", "").split(", ");

				for (int i = 0; i < 2; i++)
					for (int j = 0; j < 6; j++)
						bool[i][j] = Boolean.parseBoolean(b[(i * 6) + j]);

				Lecturer lecturer = new Lecturer(splits[0]);
				lecturer.setCourses(sb.getCourses(c));
				lecturer.setAvailability(bool);
				sb.add(lecturer);
				break;

			}
			case VENUE: {

				boolean[][] bool = new boolean[2][6];
				String[] c = splits[1].replaceAll("[\\[\\]]", "").split(", ");
				String[] b = splits[2].replaceAll("[\\[\\]]", "").split(", ");

				for (int i = 0; i < 2; i++)
					for (int j = 0; j < 6; j++)
						bool[i][j] = Boolean.parseBoolean(b[(i * 6) + j]);

				Venue venue = new Venue(splits[0], bool);
				venue.setCourses(sb.getCourses(c));
				venue.setStudentLimit(Integer.parseInt(splits[3]));
				sb.add(venue);
				break;

			}
			case SECTION: {

				Course course = new Course("");
				Lecturer lecturer = new Lecturer();
				Venue venue = new Venue();

				for (Course c : sb.courses)
					if (splits[4].equalsIgnoreCase(c.getCode())) {
						course = c;
						break;
					}

				for (Lecturer l : sb.lecturers)
					if (splits[5].equalsIgnoreCase(l.getName())) {
						lecturer = l;
						break;
					}

				for (Venue v : sb.venues)
					if (splits[6].equalsIgnoreCase(v.getName())) {
						venue = v;
						break;
					}

				Section section = new Section(Integer.parseInt(splits[0]), course, lecturer, venue, Integer.parseInt(splits[3]));
				section.setDayAndTime(Integer.parseInt(splits[1]), Integer.parseInt(splits[2]));
				section.setNote(splits[7]);
				sb.sections.add(section);
				break;

			}
		}
	}

	public void save() {

		File file = new File("temp.txt");
		File newFile = new File("schedule.txt");

		try (PrintStream fop = new PrintStream(file)) {

			fop.println("courses");
			for (Course c : sb.courses)
				fop.println(c.getDetails());

			fop.println("lecturers");
			for (Lecturer l : sb.lecturers)
				fop.println(l.getDetails());

			fop.println("venues");
			for (Venue v : sb.venues)
				fop.println(v.getDetails());

			fop.println("sections");
			for (Section s : sb.sections)
				fop.println(s.getDetails());

			fop.close();

			if (newFile.exists())
				newFile.delete();
			file.renameTo(newFile);

			System.out.println("Saved Successfully");
		} catch (Exception e) {
			System.out.println("Save Failed: " + e);
		}
	}

	public void exportHTML(){
		JFileChooser fc = new JFileChooser();
		File file = new File("");
		String htmlstart ="<html>\n" +
				"<body>\n" +
				"<table border=\"1\" cellspacing=\"1\" cellpadding=\"1\" class=\"tg-table-plain\">\n" +
				"  <tr bgcolor=\"f5f5f5\">\n" +
				"    <th>CODE</th>\n" +
				"    <th>SECTION</th>\n" +
				"    <th>TITLE</th>\n" +
				"    <th>CREDIT</th>\n" +
				"    <th>DAY</th>\n" +
				"    <th>TIME</th>\n" +
				"    <th>LECTURER</th>\n" +
				"    <th>VENUE</th>\n" +
				"  </tr>",
				htmlend = "</body>\n" +
						"</table>\n" +
						"</html>\n";

		int returnVal = fc.showDialog(null, "Save");
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
		}

		try (PrintStream fop = new PrintStream(file)) {

			fop.println(htmlstart);
			for (String[] strs : sb.getSections()){
				fop.println(
						"<tr bgcolor=\"ddddf5\">\n" +
						"    <td>" + strs[0] + "</td>\n" +
						"    <td style='text-align: center;'>" + strs[1] + "</td>\n" +
						"    <td>" + strs[2] + "</td>\n" +
						"    <td style='text-align: center;'>" + strs[3] + "</td>\n" +
						"    <td>" + strs[4] + "</td>\n" +
						"    <td>" + strs[5] + "</td>\n" +
						"    <td>" + strs[6] + "</td>\n" +
						"    <td>" + strs[7] + "</td>\n" +
						"</tr>"
				);
			}
			fop.println(htmlend);
			fop.close();

			System.out.println("Saved Successfully");
		} catch (Exception e) {
			System.out.println("Save Failed: " + e);
		}
	}
}
