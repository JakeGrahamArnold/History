package DataBase;

import java.io.File;

/**
 *
 * @author Enter-The-Dragon
 */
public class World extends EditDataBase {

    private int year;
    private int month;
    private int day;

    public World(int year, int month, int day) {

        this.year = year;
        this.month = month;
        this.day = day;

        /*
         * lets start with 256 couples
         */
        Inital(25600);

        while (this.year != 200) {

            aDay();
            nextDay();
        }


    }

    public void aDay() {

        boolean firstTime = true;

        /*
         * its a brand new day! so let go to the start of the DataBase and go to 
         * the first person. We will then go through each person.
         * 
         * Check to see if the person is alive.
         * ( if alive )
         * Get Age
         * Chance of death delt
         * 
         */

        try {

            rs.first();

            while (rs.next()) {

                /* make sure that everyone is tested! */
                if (firstTime) {
                    rs.previous();
                    firstTime = false;
                }

                /* alive? */
                int alive = checkIfAlive();

                if (alive == 0) {

                    /* get age */
                    int ageInDays = getAge();

                    /* small chance of death */
                    chanceOfDeath(ageInDays);
                }

            }

        } catch (Exception ex) {

            ex.printStackTrace();


        }
    }

    public void Inital(int numberOfCouples) {

        for (int x = 0; x < numberOfCouples; x++) {


            File file = new File("surname.txt");
            //number between 1 and 362
            randomInt = random.nextInt(360) + 1;
            String Surname = NameReader(file, randomInt);


            //0 = random, 1 = male, 2 = female
            generate(Surname, year, month, day, 1);
            generate(Surname, year, month, day, 2);
        }
    }

    /*
     * check to see if the person is alive
     * if alive == zero they are alive
     */
    public int checkIfAlive() {

        int alive = 0;

        try {

            alive = rs.getInt("Dyear");

        } catch (Exception ex) {
        }

        return alive;
    }

    /*
     * get the age of the current person
     */
    public int getAge() {

        int age = 0;

        try {

            /*
             * Lets work out the age
             */
            int byear = rs.getInt("Byear");
            int bmonth = rs.getInt("Bmonth");
            int bday = rs.getInt("Bday");

            int dayBorn = ((byear - 1) * 256);
            dayBorn += ((bmonth - 1) * 32);
            dayBorn += (bday - 1);

            int dayNumber = ((year - 1) * 256);
            dayNumber += ((month - 1) * 32);
            dayNumber += (day - 1);

            age = dayNumber - dayBorn;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return age;

    }

    /*
     * use age to give them a chance of death
     */
    public void chanceOfDeath(int age) {
        try {

            if (age < 2560) {
                // 0 - 9                     
                randomInt = random.nextInt(250000);
            } else if (age < 5120) {
                // 10 - 19                     
                randomInt = random.nextInt(85000);
            } else if (age < 7680) {
                // 20 - 29                     
                randomInt = random.nextInt(20000);
            } else if (age < 10240) {
                // 30 - 39                     
                randomInt = random.nextInt(10000);
            } else if (age < 12800) {
                // 40 - 49                     
                randomInt = random.nextInt(5000);
            } else if (age < 15360) {
                // 50 - 59                     
                randomInt = random.nextInt(2500);
            } else if (age < 17920) {
                // 60 - 69                     
                randomInt = random.nextInt(1000);
            } else if (age < 25600) {
                // 70 - 99                     
                randomInt = random.nextInt(500);
            } else if (age >= 25600) {
                // 100+
                randomInt = random.nextInt(250) ;
            }

            if (randomInt == 0) {

                rs.updateInt("Dyear", year);
                rs.updateInt("Dmonth", month);
                rs.updateInt("Dday", day);
                rs.updateRow();
                System.out.println(day + "/" + month + "/" + year);
                System.out.println("Death " + age);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /*
     * 64 seconds in a minute
     * 64 minutes in an hour 
     * 32 hours in a day  | 8 days in a week   
     * 32 days in a month | 4 weeks in a month
     * 8 months in a year | 32 weeks a year  
     * 
     * A year  | World    | Earth
     * seconds | 33554432 | 31556940
     * minutes | 524288   | 525949
     * hours   | 8192     | 8765.81
     * days    | 256      | 365
     * weeks   | 32       | 52
     * month   | 8        | 12
     * 
     */
    public void nextDay() {
        day++;

        if (day == 33) {

            month++;
            day = 1;

            /*
             * Months:
             * 1: unimensie
             * 2: duomensie
             * 3: tremensie
             * 4: quadmensie
             * 5: quinmensia
             * 6: sexamensia
             * 7: heptmensia
             * 8: octomensia
             */
            if (month == 9) {
                year++;
                month = 1;
            }
        }
    }
}
