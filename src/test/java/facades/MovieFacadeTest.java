package facades;

import utils.EMF_Creator;
import entities.Movie;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class MovieFacadeTest {

    private static EntityManagerFactory emf;
    private static MovieFacade facade;
    private static Movie movie1 = new Movie(2004, "Kung fu panda", new String[]{"Jack Black", "Angelina Jolie"});
    private static Movie movie2 = new Movie(2007, "Kung fu panda 2", new String[]{"Jack Black", "Angelina Jolie"});

    public MovieFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = MovieFacade.getMovieFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Movie.deleteAllRows").executeUpdate();
            em.persist(movie1);
            em.persist(movie2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    // TODO: Delete or change this method 
    @Test
    public void testAFacadeMethod() {
        assertEquals(2, facade.getMovieCount(), "Expects two rows in the database");
    }

//    @Test
//    public void testAddMovie() {
//        facade.addMovie(2020, "third movie", new String[]{"Test actor", "Test actress"});
//        assertEquals(3, facade.getMovieCount());
//    }
    @Test
    public void testGetAllMovies() {
        List<Movie> movies = facade.getAllMovies();
        assertEquals(2, facade.getMovieCount(), "Expects two movies in the database");

        assertThat(movies, hasItems( // or contains or containsInAnyOrder 
                Matchers.<Movie>hasProperty("title", is("Kung fu panda")),
                Matchers.<Movie>hasProperty("title", is("Kung fu panda 2"))
        )
        );
    }

    @Disabled
    @Test
    public void testGetMovieById() {
        Movie m1 = facade.getMovieById(1);
        assertThat(m1, hasProperty("id", equalTo(1L))); // OKAY AF EN ELLER ANDEN GRUND FORVENTER DEN 1L SOM ID OG IKKE BARE 1 OG JEG VED IKKE HVORFOR
    }

    @Test
    public void testGetMovieByTitle() {
        List<Movie> m1 = facade.getMovieByTitle("Kung fu panda");
        assertThat(m1, Matchers.hasSize(2)); // FORVENTER TO FILM I LISTEN
        assertEquals(2004, m1.get(1).getYear());

    }
}
