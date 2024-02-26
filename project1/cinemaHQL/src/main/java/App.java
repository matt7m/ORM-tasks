
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import entities.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

public class App {
    public static void main(String[] args) {
        System.out.println("Hibernate app\n");
        //setData();

        //.1. list of movies in cinema hall - 1/2
        List<Movie> movies = getMoviesByCinemaHallId(1);
        for (Movie movie : movies){
            System.out.println("-> " + movie.getTitle() + " : " + movie);
        }

        //.3. list of seanses with movie id
        List<Seanse> seanses = getSeanseByFilmId(5);
        if (seanses != null){
            for (Seanse seanse : seanses){
                System.out.println("-> " + seanse.getCinemaHall().getId() + " " + seanse.getMovie().getTitle() + " date: " + seanse.getDate() + " : " + seanse.getStartTime());
            }
        }

        //.4. list of seanses with movie title
        List<Seanse> seanses2 = getSeanseByFilmTitle("Superman");
        if (seanses2 != null){
            for (Seanse seanse : seanses2){
                System.out.println("-> " + seanse.getMovie().getTitle() + " date: " + seanse.getDate() + " : " + seanse.getStartTime());
            }
        }

        //.5. list of viewers on seanse - id 7
        List<User> viewers = getViewersOnSeanse(7);
        if (viewers != null){
            for (User viewer : viewers){
                System.out.println("-> " + viewer.getUsername());
            }
        }

//        //.6. list of seanses of user 1
        List<Seanse> seanses3 = getSeansesByUserId(1);
        if (seanses3 != null){
            for (Seanse seanse : seanses3){
                System.out.println("-> cinema hall - " + seanse.getCinemaHall().getId() + ", " + seanse.getMovie().getTitle() + ", " + seanse.getDate() + " : " + seanse);
            }
        }

        //.7. list of seanses of user loigin
        List<Seanse> seanses4 = getSeansesByLogin("kowalski");
        if (seanses4 != null){
            for (Seanse seanse : seanses4){
                System.out.println("-> cinema hall - " + seanse.getCinemaHall().getId() + ", " + seanse.getMovie().getTitle() + ", " + seanse.getDate() + " : " + seanse);
            }
        }

        //.8. list of seats booked by user login
        List<SeatBooked> seats = getSeatsBookedByUserLogin("kowalski");
        if (seats != null){
            for (SeatBooked seat : seats){
                System.out.println("-> seat nr " + seat.getSeat().getSeatNumber() + " in cinemaHall:" + seat.getSeanse().getCinemaHall().getId());
            }
        }

        //.8. list of booked seats in cinemahall id = 1 at seanse 4
        int result = getNumberOfBookedSeatsByCinemaHallId(3, 4);
        System.out.println(result);

        //.8. list of booked seats in seanse 2, time: 2023:06:01, 19:00
        int result2 = getBookedSeatsByHour(2, LocalTime.parse("19:00"));
        System.out.println(result2);

        //.9. number of halls with film id where is seanse
        int result3 = getNumberOfHallsWithFilmId(6);
        System.out.println(result3);

        //.10. number of booked seats by User
        int result4 = getNumberOfSeatsByUser(1);
        System.out.println(result4);

        //.10. number of booked seats by user in date between
        int result5 = getNumberOfSeatsByUserWithDate(1, LocalDate.parse("2023-05-26"), LocalDate.parse("2023-05-27"));
        System.out.println(result5);

    }

    public static void setData(){
        User user1 = new User("username1", "mail1@mail.com");
        User user2 = new User("usertest", "tempmail@gmail.com");
        User user3 = new User("kowalski", "kowalski@gmail.com");

        Movie movie1 = new Movie("Superman", "Sci-Fi", 124);
        Movie movie2 = new Movie("House", "Akcja", 91);
        Movie movie3 = new Movie("Movie 3", "Akcja", 109);
        Movie movie4 = new Movie("Movie 4", "Akcja", 76);
        Movie movie5 = new Movie("Cartoon", "Bajka", 83);
        Movie movie6 = new Movie("Spiderman", "Akcja", 143);

        CinemaHall cinemaHall1 = new CinemaHall();
        CinemaHall cinemaHall2 = new CinemaHall();

        Seanse seanse1 = new Seanse();
        Seanse seanse2 = new Seanse();
        Seanse seanse3 = new Seanse();
        Seanse seanse4 = new Seanse();

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();

            // save users
            session.persist(user1);session.persist(user2);session.persist(user3);

            //save movies
            session.persist(movie1);session.persist(movie2);session.persist(movie3);session.persist(movie4);session.persist(movie5);session.persist(movie6);

            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //.1/2.
    public static List<Movie> getMoviesByCinemaHallId(long cinemaHallId){
        List<Movie> movies = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            movies = session.createQuery("select m from Movie m inner join Seanse s on m.id = s.movie.id inner join CinemaHall ch on s.cinemaHall.id = ch.id where ch.id =: parameter", Movie.class)
                    .setParameter("parameter", cinemaHallId)
                    .list();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movies;
    }

    //.3.
    public static List<Seanse> getSeanseByFilmId(long movieId){
        List<Seanse> seanses = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            seanses = session.createQuery("select s from Seanse s where s.movie.id =: parameter", Seanse.class)
                    .setParameter("parameter", movieId)
                    .setFirstResult(0)
                    .setMaxResults(2)
                    .list();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return seanses;
    }

    //.4.
    public static List<Seanse> getSeanseByFilmTitle(String filmTitle){
        List<Seanse> seanses = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            seanses = session.createQuery("select s from Seanse s where s.movie.title =: parameter", Seanse.class)
                    .setParameter("parameter", filmTitle)
                    .setFirstResult(0)
                    .setMaxResults(4)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return seanses;
    }

    //.5.
    public static List<User> getViewersOnSeanse(long seanseId){
        List<User> users = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            users = session.createQuery("select u from User u inner join Booking b on b.user.id = u.id inner join Seanse s on s.id = b.seanse.id where s.id  =: parameter", User.class)
                    .setParameter("parameter", seanseId)
                    .list();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    //.6.
    public static List<Seanse> getSeansesByUserId(long userId){
        List<Seanse> seanses = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            seanses = session.createQuery("select s from Seanse s, Booking b, User u where s.id = b.seanse.id and b.user.id = u.id and u.id  =: parameter", Seanse.class)
                    .setParameter("parameter", userId)
                    .list();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return seanses;
    }

    //.7.
    public static List<Seanse> getSeansesByLogin(String login){
        List<Seanse> seanses = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            seanses = session.createQuery("select s from Seanse s, Booking b, User u where s.id = b.seanse.id and b.user.id = u.id and u.username  =: parameter", Seanse.class)
                    .setParameter("parameter", login)
                    .list();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return seanses;
    }

    //.8.
    public static int getNumberOfBookedSeatsByCinemaHallId(long cinemaHallId, long seanseId){
        List<SeatBooked> seats = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            seats = session.createQuery("select sb from SeatBooked sb inner join Booking b on sb.booking.id = b.id inner join Seanse s on b.seanse.id = s.id where s.cinemaHall.id =: parameter", SeatBooked.class)
                    .setParameter("parameter", cinemaHallId)
                    .list();

        } catch (Exception e) {
            e.printStackTrace();
        }

        assert seats != null;
        return seats.size();
    }

    //.8.
    public static int getBookedSeatsByHour(long seanseId, LocalTime time){
        List<SeatBooked> seats = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            seats = session.createQuery("select sb from SeatBooked sb inner join Booking b on sb.booking.id = b.id inner join Seanse s on b.seanse.id = s.id where s.id  =: parameter and :time between s.startTime and s.endTime", SeatBooked.class)
                    .setParameter("parameter", seanseId)
                    .setParameter("time", time)
                    .list();

        } catch (Exception e) {
            e.printStackTrace();
        }

        assert seats != null;
        return seats.size();
    }

    //.8.
    public static List<SeatBooked> getSeatsBookedByUserLogin(String login){
        List<SeatBooked> seats = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            seats = session.createQuery("select sb from SeatBooked sb inner join Booking b on sb.booking.id = b.id inner join User u on b.user.id = u.id where u.username  =: parameter", SeatBooked.class)
                    .setParameter("parameter", login)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return seats;
    }

    //.9.
    public static int getNumberOfHallsWithFilmId(long filmId){
        List<CinemaHall> cinemaHalls = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            cinemaHalls = session.createQuery("select ch from CinemaHall ch inner join Seanse s on s.cinemaHall.id = ch.id inner join Movie m on s.movie.id = m.id where m.id =: parameter", CinemaHall.class)
                    .setParameter("parameter", filmId)
                    .list();

        } catch (Exception e) {
            e.printStackTrace();
        }

        assert cinemaHalls != null;
        return cinemaHalls.size();
    }

    //.10.
    public static int getNumberOfSeatsByUser(long userId){
        List<SeatBooked> seatsBooked = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            seatsBooked = session.createQuery("select sb from SeatBooked sb inner join Booking b on sb.booking.id = b.id inner join User u on b.user.id = u.id where u.id =: parameter", SeatBooked.class)
                    .setParameter("parameter", userId)
                    .list();

        } catch (Exception e) {
            e.printStackTrace();
        }

        assert seatsBooked != null;
        return seatsBooked.size();
    }

    //.101.
    public static int getNumberOfSeatsByUserWithDate(long userId, LocalDate firstRange, LocalDate secondRange){
        List<SeatBooked> seatsBooked = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            seatsBooked = session.createQuery("select sb from SeatBooked sb inner join Booking b on sb.booking.id = b.id inner join User u on b.user.id = u.id where u.id =: parameter and b.bookingTime between :date1 and :date2", SeatBooked.class)
                    .setParameter("parameter", userId)
                    .setParameter("date1", firstRange)
                    .setParameter("date2", secondRange)
                    .list();

        } catch (Exception e) {
            e.printStackTrace();
        }

        assert seatsBooked != null;
        return seatsBooked.size();
    }
}
