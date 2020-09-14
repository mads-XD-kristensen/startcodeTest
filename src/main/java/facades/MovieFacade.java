package facades;

import entities.Movie;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import utils.EMF_Creator;

public class MovieFacade {

    private static MovieFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private MovieFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static MovieFacade getMovieFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MovieFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //TODO Remove/Change this before use
    public long getMovieCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long movieCount = (long) em.createQuery("SELECT COUNT(r) FROM Movie r").getSingleResult();
            return movieCount;
        } finally {
            em.close();
        }
    }

    public List<Movie> getAllMovies() {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT m FROM Movie m");
            List<Movie> movieList = query.getResultList();
            return movieList;
        } finally {
            em.close();
        }
    }

//    public void addMovie(int year, String title, String[] actors) {
//        EntityManager em = emf.createEntityManager();
//        Movie m = new Movie(year, title, actors);
//        try {
//            em.getTransaction().begin();
//            em.persist(m);
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
//    }

    public Movie getMovieById(long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT m FROM Movie m WHERE m.id = :id");
            query.setParameter("id", id);
            Movie movie = (Movie) query.getSingleResult();
            return movie;
        } finally {
            em.close();
        }
    }

    public List<Movie> getMovieByTitle(String title) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT m FROM Movie m WHERE m.title LIKE CONCAT('%',:title,'%')");
            query.setParameter("title", title);
            List<Movie> movieList = query.getResultList();
            return movieList;
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {

        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE from Movie").executeUpdate();
            em.persist(new Movie(2003, "Kung fu panda", new String[]{"Jack black", "Angelina Jolie"}));
            em.persist(new Movie(2006, "Kung fu panda 2", new String[]{"Jack black", "Angelina Jolie"}));
            em.persist(new Movie(2009, "Kung fu panda 3", new String[]{"Jack black", "Angelina Jolie"}));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

}
