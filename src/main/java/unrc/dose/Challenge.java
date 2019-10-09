package unrc.dose;

import java.io.File;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileWriter;
import org.javalite.activejdbc.Model;

/**
 *  table attributes:.
 *  id integer auto_increment primary key,
 *  user_id: integer,
 *  title varchar (50),
 *  description varchar(50),
 *  source varchar(10000),
 *  point integer,
 *  owner_solution_id integer
 */
public class Challenge extends Model {

    /**
     * the class constructor.
     */
    public Challenge() { }

    /**
     * method that returns the id of a user.
     * @return user id.
     */
    public int getUserId() {
        return getInteger("user_id");
    }

    /**
     * method to modify user id.
     * @param userId user id that created it.
     */
    public void setUserId(final int userId) {
        set("user_id", userId);
    }

    /**
     * method that returns the title of a challenge.
     * @return title of a challenge.
     */
    public String getTitle() {
        return getString("title");
    }

    /**
     * method to modify the challenge title.
     * @param title title that will have the challenge.
     */
    public void setTitle(final String title) {
        set("title", title);
    }

    /**
     * method that returns the description of a challenge.
     * @return description of a challenge.
     */
    public String getDescription() {
        return getString("description");
    }

    /**
     * method to modify the challenge description.
     * @param description a brief description of what the challenge is about.
     */
    public void setDescription(final String description) {
        set("description", description);
    }

    /**
     * method that returns the source of a challenge.
     * @return source of a challenge.
     */
    public String getSource() {
        return getString("source");
    }

    /**
     * method to modify the challenge source.
     * @param source source code that will have the challenge.
     */
    public void setSource(final String source) {
        set("source", source);
    }

    /**
     * method that returns the point of a challenge.
     * @return point of a challenge.
     */
    public int getPoint() {
        return getInteger("point");
    }

    /**
     * method to modify the challenge point.
     * @param point points given by the admin that for the challenge.
     */
    public void setPoint(final int point) {
        set("point", point);
    }

    /**
     * method that returns the owner solution id of a challenge.
     * @return owner solution id of a challenge.
     */
    public int getOwnerSolutionId() {
        return getInteger("owner_solution_id");
    }

    /**
     * method to modify the challenge owner solution id.
     * @param ownerSolutionId id of the solution proposed by a user.
     */
    public void setOwnerSolutionId(final int ownerSolutionId) {
        set("owner_solution_id", ownerSolutionId);
    }

    /**
     * This method allows to create a challenge.
     * @param userId user id that created it.
     * @param title title that will have the challenge.
     * @param description a brief description of what the challenge is about.
     * @param source source code that will have the challenge.
     * @param point points given by the admin that for the challenge.
     * @param ownerSolutionId id of the solution proposed by a user.
     * @return challenge created
     */
    public static Challenge addChallenge(
        final int userId,
        final String title,
        final String description,
        final String source,
        final int point,
        final int ownerSolutionId) {
        Challenge c = new Challenge();
        c.setUserId(userId);
        c.setTitle(title);
        c.setDescription(description);
        c.setSource(source);
        c.setPoint(point);
        c.setOwnerSolutionId(ownerSolutionId);
        c.saveIt();
        return c;
    }

    /**
     * This method allows you to create a test challenge and validate it.
     * @param userId user id that created it.
     * @param title title that will have the challenge.
     * @param description a brief description of what the challenge is about.
     * @param source source code that will have the challenge.
     * @param point points given by the admin that for the challenge.
     * @param ownerSolutionId id of the solution proposed by a user.
     * @param test test corresponding to the challenge.
     * @return true in case the validation is successful, otherwise false.
     */
    public static boolean addTestChallenge(
        final int userId,
        final String title,
        final String description,
        final String source,
        final int point,
        final int ownerSolutionId,
        final String test) {
        Challenge c = addChallenge(
            userId,
            title,
            description,
            source,
            point,
            ownerSolutionId);
        TestChallenge t = TestChallenge.addTestChallenge(
            c.getInteger("id"),
            test);
        return (TestChallenge.validateTestChallenge(c, t));
    }

    /**
     * This method allows you to create a compilation challenge and validate it.
     * @param userId user id that created it.
     * @param title title that will have the challenge.
     * @param description a brief description of what the challenge is about.
     * @param source source code that will have the challenge.
     * @param point points given by the admin that for the challenge.
     * @param ownerSolutionId id of the solution proposed by a user.
     * @return true in case the validation is successful, otherwise false.
     */
    public static boolean addCompilationChallenge(
        final int userId,
        final String title,
        final String description,
        final String source,
        final int point,
        final int ownerSolutionId) {
        Challenge c = addChallenge(
            userId,
            title,
            description,
            source,
            point,
            ownerSolutionId);
        CompilationChallenge.addCompilationChallenge(c.getInteger("id"));
        return (CompilationChallenge.validateCompilationChallenge(c));
    }

    /**
     * This method allows you to delete a challenge created.
     * @param c challenge to eliminate.
     */
    public static void deleteChallenge(final Challenge c) {
        c.deleteCascade();
    }

    /**
     * method to generate the java file of the challenge.
     * @param name file name.
     * @param source source file.
     * @return file name.
     */
    public static String generateFileJava(
        final String name,
        final String source) {
        try {
            String nameFile = "/tmp/" + name + ".java";
            File file = new File(nameFile);
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(source);
            bw.close();
            return nameFile;
        } catch (IOException e) {
            throw new IllegalArgumentException("Error the read file");
        }
    }

    /**
     * method for execute the command (javac and java).
     * @param command command to execute
     * @return 0 if run otherwise 1.
     */
    public static int runProcess(final String command) {
        try {
            Process pro = Runtime.getRuntime().exec(command);
            pro.waitFor();
            return pro.exitValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

}
