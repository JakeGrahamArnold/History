package DataBase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Random;

public class EditDataBase extends DataBase {

    public Random random;
    public int randomInt;

    /**
     * constructor for the edit database class
     */
    public EditDataBase() {
       /**
         * add new info to the data base
         */
        random = new Random();
        
    }

    public void generate(String lname, int byear, int bmonth, int bday, int gen) {




        String fname;
        String gender;
        int dyear = 0;
        int dmonth = 0;
        int dday = 0;
        boolean male = false;

        /*
         * First work out the Sex if not already set
         */
        switch (gen) {

            case 0:
                male = gender();
                break;
            case 1:
                male = true;
                break;
            case 2:
                male = false;
                break;
        }


        if (male) {
            gender = "male";
        } else {
            gender = "female";
        }

        /*
         * Find the Name
         * male == true
         * female == false
         */
        fname = firstName(male);

        /*
         * Add the new person in the DataBase
         */
        Add(fname, lname, gender, byear, bmonth, bday, dyear, dmonth, dday);

    }

    public boolean gender() {

        randomInt = random.nextInt(1);

        if (randomInt == 0) {
            return false;
        } else {
            return true;
        }
    }

    public String firstName(boolean male) {

        String firstName = null;




        File file;
        if (male) {
            file = new File("male.txt");
            //number between 1 and 2943
            randomInt = random.nextInt(2942) + 1;
        } else {
            file = new File("female.txt");
            //number between 1 and 5001
            randomInt = random.nextInt(5000) + 1;
        }

        firstName = NameReader(file, randomInt);

        return firstName;
    }

    /*
     * Add a new person to the data base
     */
    public void Add(
            String fname,
            String lname,
            String gender,
            int byear,
            int bmonth,
            int bday,
            int dyear,
            int dmonth,
            int dday) {

        try {

            /* Add new info to the data base */

            rs.moveToInsertRow();
            rs.updateString("Fname", fname);
            rs.updateString("Lname", lname);
            rs.updateString("Gender", gender);
            rs.updateInt("Byear", byear);
            rs.updateInt("Bmonth", bmonth);
            rs.updateInt("Bday", bday);
            rs.updateInt("Dyear", dyear);
            rs.updateInt("Dmonth", dmonth);
            rs.updateInt("Dday", dday);
            rs.insertRow();

//            while (rs.next()) {
//
//
//                System.out.println(
//                        rs.getString("Fname") + " "
//                        + rs.getString("Lname") + " "
//                        + rs.getString("Gender") + " ");
//            }
            closeandreload();

        } catch (Exception e) {
        }
    }

    public void closeandreload() {
        try {
            //close
            rs.close();
            st.close();
            //reOpen
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "select * from History";
            rs = st.executeQuery(sql);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String NameReader(File file, int i) {

        String name = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            for (int x = 0; x < i; x++) {
                name = br.readLine();
            }
            br.close();

        } catch (IOException e) {
            // Something went wrong, eg: file not found
            e.printStackTrace();
        }
        return name;
    }
}
