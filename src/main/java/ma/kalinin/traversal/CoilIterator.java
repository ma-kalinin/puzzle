package ma.kalinin.traversal;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Iterate a n x n array in a coli-like manner, starting from the outside
 * <a href="https://www.codewars.com/kata/521c2db8ddc89b9b7a0000c1">more details</a>
 */
@UtilityClass
public class CoilIterator {

    @RequiredArgsConstructor
    private enum Direction {
        RIGHT(1, 0), DOWN(0, 1), LEFT(-1, 0), UP(0, -1);

        final int horizontalShift, verticalShift;

        Direction nextClockwise() {
            return switch (this) {
                case RIGHT -> DOWN;
                case DOWN -> LEFT;
                case LEFT -> UP;
                case UP -> RIGHT;
            };
        }
    }

    @AllArgsConstructor
    private static class Pointer {
        private int horizontal, vertical;

        void move(Direction direction) {
            this.horizontal += direction.horizontalShift;
            this.vertical += direction.verticalShift;
        }
    }

    public static int[] snail(int[][] square) {
        if (square[0].length == 0) return new int[] {};    //zero-length corner case

        var position = new Pointer(0, 0);
        var direction = Direction.RIGHT;
        var result = new ArrayList<Integer>(square.length * square.length);

        for (var length : straightPathLengths(square.length)) {
            for (int steps = length - 1; steps > 0; --steps) {
                result.add(square[position.vertical][position.horizontal]);
                position.move(direction);
            }
            direction = direction.nextClockwise();
        }
        result.add(square[position.vertical][position.horizontal]);    //pick up the last one

        return result.stream().mapToInt(Integer::intValue).toArray();
    }

    // when traversing a square field of cells from the outside in a coil-like manner,
    // first three straight paths will have full length, and each following pair of paths will be shorter by one cell
    private static List<Integer> straightPathLengths(int squareSideLength) {
        var first = squareSideLength;
        var second = squareSideLength;
        var third = squareSideLength;
        var result = new ArrayList<Integer>();

        while (first != 0) {
            result.add(first);
            first = second;
            second = third;
            third = (first == second) ? third - 1 : third;
        }

        return result;
    }
}
