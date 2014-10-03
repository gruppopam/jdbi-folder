package in.folder.jdbi.dao;

import in.folder.jdbi.DaoTest;
import in.folder.jdbi.model.Movie;
import in.folder.jdbi.model.Song;
import in.folder.jdbi.model.Team;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SqlObjectDaoTest extends DaoTest {
    private SqlObjectDao dao;

    @Before
    public void setUp() {
        dao = handle.attach(SqlObjectDao.class);
    }

    @Test(expected = RuntimeException.class)
    public void shouldTakeRegisteredMapper() throws Exception {
        Movie jeans = Movie.builder().movieId(1).movieName("Jeans").build();
        insert(jeans);

        dao.getMovies();
    }

    @Test
    public void shouldUseOfCustomMappingFactory() throws Exception {
        Song song1 = Song.builder().songId(1).songName("Anbe Anbe").build();
        Song song2 = Song.builder().songId(2).songName("Columbus Columbus").build();
        insert(song1, song2);

        List<Song> songs = dao.getSongs();

        assertEquals(2, songs.size());
    }

    @Test
    public void shouldUseColumnNameWhileMapping() throws Exception {
        Team csk = Team.builder().teamId(1).teamName("CSK").build();
        Team rcb = Team.builder().teamId(2).teamName("RCB").build();
        Team mi  = Team.builder().teamId(3).teamName("MI") .build();
        insert(csk, rcb, mi);

        List<Team> teams = dao.getTeam();

        assertEquals(3, teams.size());
        assertEquals(new Integer(1), teams.get(0).getTeamId());
        assertEquals("CSK", teams.get(0).getTeamName());
        assertEquals(new Integer(2), teams.get(1).getTeamId());
        assertEquals("RCB", teams.get(1).getTeamName());
        assertEquals(new Integer(3), teams.get(2).getTeamId());
        assertEquals("MI", teams.get(2).getTeamName());
    }

    @Test
    public void shouldUseCustomMapperForFactoryForBigDecimal(){
        Team csk = new Team(1,"CSK",BigDecimal.ONE);
        insert(csk);

        List<Team> teams = dao.getTeam();

        assertEquals(1, teams.size());
        assertEquals(BigDecimal.TEN, teams.get(0).getAverage());
    }
}
