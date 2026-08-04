package nl.tudelft.jpacman.level;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;

import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.sprite.Sprite;

/**
 * Tests the multiple-lives behaviour of {@link PlayerCollisions} when a
 * player collides with a ghost.
 */
class PlayerCollisionsTest {

    /**
     * The strategy used to compute points, not relevant to these tests.
     */
    private final PointCalculator pointCalculator = mock(PointCalculator.class);

    /**
     * The square the player respawns on. A real (bare-bones) square is used
     * rather than a mock, since {@link Unit#occupy(Square)} relies on the
     * square actually tracking its occupants.
     */
    private final Square startSquare = new TestSquare();

    /**
     * The ghost the player collides with.
     */
    private final Ghost ghost = mock(Ghost.class);

    /**
     * The collision map under test.
     */
    private PlayerCollisions collisions;

    /**
     * The player involved in the collisions.
     */
    private Player player;

    /**
     * Creates a player standing on some square, and a collision map with a
     * single start square to respawn on.
     */
    @BeforeEach
    void setUp() {
        List<Square> startSquares = Lists.newArrayList(startSquare);
        collisions = new PlayerCollisions(pointCalculator, startSquares);
        player = new PlayerFactory(new PacManSprites()).createPacMan();
        player.occupy(new TestSquare());
    }

    /**
     * A player that still has lives left should respawn on the start square
     * and remain alive after colliding with a ghost.
     */
    @Test
    void respawnsWhenLivesRemain() {
        collisions.playerVersusGhost(player, ghost);

        assertThat(player.hasLivesRemaining()).isTrue();
        assertThat(player.isAlive()).isTrue();
        assertThat(player.getSquare()).isEqualTo(startSquare);
    }

    /**
     * A player that loses their last life should stay dead, with the ghost
     * that caused the death recorded as the killer.
     */
    @Test
    void staysDeadWhenLastLifeIsLost() {
        int maxLives = 3;
        for (int i = 0; i < maxLives; i++) {
            collisions.playerVersusGhost(player, ghost);
        }

        assertThat(player.hasLivesRemaining()).isFalse();
        assertThat(player.isAlive()).isFalse();
        assertThat(player.getKiller()).isEqualTo(ghost);
    }

    /**
     * A minimal, always-accessible square, needed because {@link Unit#occupy}
     * requires a real square that tracks its occupants.
     */
    private static final class TestSquare extends Square {

        @Override
        public boolean isAccessibleTo(Unit unit) {
            return true;
        }

        @Override
        public Sprite getSprite() {
            return null;
        }
    }
}
