package unrc.dose;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

/**
 * Table challenges - Attributes.
 * id integer auto_increment primary key.
 * user_id: integer not null.
 * title varchar (50) not null.
 * description varchar(50).
 * source varchar(10000) not null.
 * point integer not null.
 * owner_solution_id integer not null.
 * class_name varchar (30) not null.
 * @author Brusati Formento, Matias
 * @author Cuesta, Alvaro
 */
public class Challenge extends Model {

    /**
     * message that will throw the exception if you want to
     * modify a resolved challenge.
     */
    public static final String CHALLENGE_RESOLVED = "The challenge is solved";
     /**
     * message that will throw the exception if the challenge
     * to delete does not exist.
     */
    public static final String CHALLENGE_NOT_EXIST =
    "The challenge is not exist";

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
     *  method that returns the id of a user.
     * @return class name.
     */
    public String getClassName() {
        return getString("class_name");
    }

    /**
     *  method to modify the class name.
     * @param className class name of a challenge.
     */
    public void setClassName(final String className) {
        set("class_name", className);
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
     * @param className title for class and name file.
     * @param description a brief description of what the challenge is about.
     * @param source source code that will have the challenge.
     * @param point points given by the admin that for the challenge.
     * @param ownerSolutionId id of the solution proposed by a user.
     * @return challenge created
     */
    public static Challenge addChallenge(
        final int userId,
        final String title,
        final String className,
        final String description,
        final String source,
        final int point,
        final int ownerSolutionId) {
        Challenge c = new Challenge();
        c.setUserId(userId);
        c.setTitle(title);
        c.setClassName(className);
        c.setDescription(description);
        c.setSource(source);
        c.setPoint(point);
        c.setOwnerSolutionId(ownerSolutionId);
        c.saveIt();
        return c;
    }

    /**
     * this method verifies if the challenge exists.
     * @param c challenge to verifies.
     */
    public static void validatePresenceChallenge(final Challenge c) {
        if (c == null) {
            throw new IllegalArgumentException(CHALLENGE_NOT_EXIST);
        }
    }

    /**
     * This method allows you to delete a challenge created.
     * @param id id of tho challenge to eliminate.
     */
    public static void deleteChallenge(final int id) {
        Challenge c = Challenge.findFirst("id = ?", id);
        validatePresenceChallenge(c);
        c.deleteCascade();
    }

    /**
     * method that returns a list of the challenges associated with the user.
     * @param userId user id to be treated.
     * @return list of challenges associated with the user.
     */
    public static LazyList<Challenge> viewUserAssociatedChallange(
        final int userId) {
        return Challenge.where("user_id = ?", userId);
    }

    /**
     * method to generate the java file of the challenge.
     * @param name file name.
     * @param source source file.
     * @return true generate correct.
     */
    public static boolean generateFileJava(
        final String name,
        final String source) {
        try {
            File folderSrc = new File("/../tmp/src");
            if (!folderSrc.exists() || !folderSrc.isDirectory()) {
                runProcess("mkdir /../tmp/src");
            }
            File folderMain = new File("/../tmp/src/main");
            if (!folderMain.exists() || !folderMain.isDirectory()) {
                runProcess("mkdir /../tmp/src/main");
            }
            File folderTest = new File("/../tmp/src/test");
            if (!folderTest.exists() || !folderTest.isDirectory()) {
                runProcess("mkdir /../tmp/src/test");
            }
            String nameFile = "/../tmp/src/main/" + name + ".java";
            File file = new File(nameFile);
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(source);
            bw.close();
            return true;
        } catch (IOException e) {
            throw new IllegalArgumentException("Error the read file");
        }
    }


    /**
     * method to generate the java file test of the challenge.
     * @param name name test file.
     * @param test test file.
     * @return true generate correct.
     */
    public static boolean generateFileJavaTest(
        final String name,
        final String test) {
        try {
            File folderSrc = new File("/../tmp/src");
            if (!folderSrc.exists() || !folderSrc.isDirectory()) {
                runProcess("mkdir /../tmp/src");
            }
            File folderMain = new File("/../tmp/src/main");
            if (!folderMain.exists() || !folderMain.isDirectory()) {
                runProcess("mkdir /../tmp/src/main");
            }
            File folderTest = new File("/../tmp/src/test");
            if (!folderTest.exists() || !folderTest.isDirectory()) {
                runProcess("mkdir /../tmp/src/test");
            }
            String nameFile = "/../tmp/src/test/" + name + ".java";
            File file = new File(nameFile);
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(test);
            bw.close();
            return true;
        } catch (IOException e) {
            throw new IllegalArgumentException("Error the read file");
        }
    }

    /**
     * method for execute the command (javac and java).
     * @param command command to execute
     * @return true if run otherwise false.
     */
    public static boolean runProcess(final String command) {
        try {
            Process pro = Runtime.getRuntime().exec(command);
            pro.waitFor();
            return (pro.exitValue() == 0);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method will allow you to compile a java program.
     * @param nameFile name of the file to compile.
     * @return true if run otherwise false.
     */
    public static boolean runCompilation(final String nameFile) {
        return runProcess("javac /../tmp/src/main/" + nameFile + ".java");
    }

    /**
     * This method will allow you to run a java program.
     * @param nameFile name of the file to execute.
     * @return true if run otherwise false.
     */
    public static boolean runJava(final String nameFile) {
        return runProcess("java -cp .:/tmp/ src.main." + nameFile);
    }

    /**
     * this method checks if a challenge was solved.
     * @param idChallege id of the challenge to check.
     */
    public static void checkUnsolvedChallenge(final int idChallege) {
        if (Proposition.where("challenge_id = ? and isSolution = 1",
        idChallege).size() != 0) {
            throw new RuntimeException(CHALLENGE_RESOLVED);
        }
    }

    /**
     * This method arms the json correctly to return.
     * @return a challenge.
     */
    public Map<String, Object> toJson() {
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("id", this.getInteger("id"));
        ret.put("user_id", this.getInteger("user_id"));
        ret.put("title", this.getString("title"));
        ret.put("class_name", this.getString("class_name"));
        ret.put("description", this.getString("description"));
        ret.put("source", this.getString("source"));
        ret.put("point", this.getInteger("point"));
        ret.put("owner_solution_id", this.getString("owner_solution_id"));
        return ret;
    }

    /**
     * hashCode redefined.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.getInteger("id").hashCode();
        return result;
    }

    /**
     * this method to compare two challenges according to id.
     * @param c challenge as a parameter.
     * @return true if the id of challenges are equal.
     */
    @Override
    public boolean equals(final Object c) {
        Challenge tc = (Challenge) c;
        return getInteger("id").equals(tc.getInteger("id"));
    }

}
