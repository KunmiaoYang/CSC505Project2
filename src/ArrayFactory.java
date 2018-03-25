import java.util.*;

/**
 *
 * Created by Kunmiao Yang on 2/1/2018.
 */
abstract public class ArrayFactory {
    private static ArrayFactory randomFactory = ArrayFactory.getRandomFactory();

    abstract Integer[] createArray(int size);
    abstract ArrayList<Integer> createArrayList(int size);

    public static ArrayFactory getRandomFactory() {
        return new ArrayFactory() {
            @Override
            Integer[] createArray(int size) {
                Integer[] array = new Integer[size];
                Random random = new Random();
                for(int i = 0; i < size; i++) array[i] = random.nextInt(Integer.MAX_VALUE) + 1;
                return array;
            }

            @Override
            ArrayList<Integer> createArrayList(int size) {
                ArrayList<Integer> array = new ArrayList<>(size);
                Random random = new Random();
                for(int i = 0; i < size; i++) array.add(random.nextInt());
                return array;
            }
        };
    }

    public static ArrayFactory getSortedFactory() {
        return new ArrayFactory() {
            @Override
            Integer[] createArray(int size) {
                Integer[] array = randomFactory.createArray(size);
                Arrays.sort(array);
                return array;
            }

            @Override
            ArrayList<Integer> createArrayList(int size) {
                ArrayList<Integer> array = new ArrayList<>(size);
                for(int i = 1; i <= size; i++) array.add(i);
                return array;
            }
        };
    }

    public static ArrayFactory getReverseFactory() {
        return new ArrayFactory() {
            @Override
            Integer[] createArray(int size) {
                Integer[] array = randomFactory.createArray(size);
                Arrays.sort(array, Collections.reverseOrder());
                return array;
            }

            @Override
            ArrayList<Integer> createArrayList(int size) {
                ArrayList<Integer> array = new ArrayList<>(size);
                for(int i = size; i > 0; i--) array.add(i);
                return array;
            }
        };
    }

    public static ArrayFactory getSawtoothFactory() {
        return new ArrayFactory() {
            @Override
            Integer[] createArray(int size) {
                Integer[] array = randomFactory.createArray(size);
                Arrays.sort(array, 0, size/2);
                Arrays.sort(array, size/2, size);
//                System.arraycopy(array, 0, array, size/2, size/2);
                return array;
            }

            @Override
            ArrayList<Integer> createArrayList(int size) {
                ArrayList<Integer> array = new ArrayList<>(size);
                for(int i = 1; i <= size/2; i++) array.add(i);
                for(int i = 1; i <= size/2; i++) array.add(i);
                return array;
            }
        };
    }

    public static ArrayFactory getOrganPipeFactory() {
        return new ArrayFactory() {
            @Override
            Integer[] createArray(int size) {
                Integer[] array = randomFactory.createArray(size);
                Arrays.sort(array, 0, size/2);
                System.arraycopy(array, 0, array, size / 2, size / 2);
                Arrays.sort(array, size/2, size, Collections.reverseOrder());
                return array;
            }

            @Override
            ArrayList<Integer> createArrayList(int size) {
                ArrayList<Integer> array = new ArrayList<>(size);
                for(int i = 1; i <= size/2; i++) array.add(i);
                for(int i = size/2; i >= 1; i--) array.add(i);
                return array;
            }
        };
    }

    public static void main(String[] args) {
        ArrayFactory f = getOrganPipeFactory();
        System.out.println(Arrays.toString(f.createArray(8)));
    }
}
