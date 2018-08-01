package cz.upce.inui.dreamteam.service;

import cz.upce.inui.dreamteam.state.City;
import cz.upce.inui.dreamteam.state.Station;

import java.util.HashSet;
import java.util.Set;

/**
 * Finds a city that is fully guarded.
 */
public class GuardedCityFinder {
    /**
     * Finds a city that is fully guarded. Searches all k-combinations of stations and returns first matching city.
     *
     * @return City that is fully guarded.
     */
    public City findGuardedCity() {
        City city = CityFactory.instance().getGuardedCity();
        Set<Station[]> combinationsOfStationsToGuard = getAllSubsetsOfThree(city.getStations());
        for (Station[] stationsToGuard : combinationsOfStationsToGuard) {
            City newCity = getGuardedCity(stationsToGuard);
            if (newCity.isWholeCityGuarded()) {
                return newCity;
            }
        }
        return null;
    }

    /**
     * Creates new city that is guarded based on method input.
     *
     * @param stationsToGuard Stations to guard in newly created city.
     * @return Created city.
     */
    private City getGuardedCity(Station[] stationsToGuard) {
        City newCity = CityFactory.instance().getEmptyCity();
        for (Station station : stationsToGuard) {
            newCity.guardStation(station);
        }
        return newCity;
    }

    /**
     * Retrieves all k-subsets where k=3
     * <p>
     * Source of original algorithm: http://stackoverflow.com/questions/29910312/algorithm-to-get-all-the-combinations-of-size-n-from-an-array-java
     * </p>
     *
     * @param cityStations City stations to make subsets from
     * @return All k-subsets
     */
    private Set<Station[]> getAllSubsetsOfThree(Set<Station> cityStations) {
        final int sizeOfSubset = 3;
        Station[] stations = cityStations.toArray(new Station[cityStations.size()]);
        Set<Station[]> combinations = new HashSet<>();
        int[] indices = initializeIndices(sizeOfSubset);
        combinations.add(getSubset(stations, indices));
        while (true) {
            int index = findIncrementableIndex(stations.length, sizeOfSubset, indices);
            if (index < 0) {
                break;
            } else {
                incrementAllIndexesToRight(sizeOfSubset, indices, index);
                combinations.add(getSubset(stations, indices));
            }
        }
        return combinations;
    }

    /**
     * Initializes array that contains indices used for subset creation.
     * For k = 3 resulting array is {0, 1, 2}
     *
     * @param sizeOfSubset Size of subset
     * @return New array of indexes
     */
    private int[] initializeIndices(int sizeOfSubset) {
        int[] indices = new int[sizeOfSubset];
        for (int i = 0; i < sizeOfSubset; i++) {
            indices[i] = i;
        }
        return indices;
    }

    /**
     * Finds index of rightmost incrementable element in array that contains indices.
     * <p>
     * <ul>
     * <li>For index array {1 2 3} and stationsNumber = 4 it returns 2, as the last element can be incremented. This will later result in array {1 2 4}.</li>
     * <li>For index array {1 2 4} and stationsNumber = 4 it returns 1, as the last element cannot be incremented. This will later result in array {1 3 4}.</li>
     * </ul>
     * </p>
     *
     * @param stationsNumber Number of all stations
     * @param sizeOfSubset   Size of subset
     * @param indices        Array of indexes
     * @return Index of incrementable element or -1 of no such element exists.
     */
    private int findIncrementableIndex(int stationsNumber, int sizeOfSubset, int[] indices) {
        int index = sizeOfSubset - 1;
        while (index >= 0 && indices[index] == stationsNumber - sizeOfSubset + index) {
            index--;
        }
        return index;
    }

    /**
     * Increments values in input array and sets all values to the right in ascending fashion.
     * <p>
     * <ul>
     * <li> For index array {1, 2, 3} and index 1 this method returns {1, 3, 4}.</li>
     * <li> For index array {1, 5, 3} and index 1 this method returns {1, 6, 7}.</li>
     * </ul>
     * </p>
     *
     * @param sizeOfSubset Size of subset
     * @param indices      Array of indexes
     * @param index        Index of element that is incremented
     */
    private void incrementAllIndexesToRight(int sizeOfSubset, int[] indices, int index) {
        indices[index]++;
        for (++index; index < sizeOfSubset; index++) {
            indices[index] = indices[index - 1] + 1;
        }
    }

    /**
     * Generates subset of stations using input index array.
     * <p>
     * <ul>
     * <li>For input {A, B, C, D} and indexes {0, 1, 2} this method creates {A, B, C}.</li>
     * <li>For input {A, B, C, D} and indexes {2, 1, 3} this method creates {C, B, D}.</li>
     * </ul>
     * </p>
     *
     * @param input   Array of all stations from which the indexes is created
     * @param indexes Index array which is used for subset creation
     * @return Generated subset
     */
    private Station[] getSubset(Station[] input, int[] indexes) {
        Station[] result = new Station[indexes.length];
        for (int i = 0; i < indexes.length; i++)
            result[i] = input[indexes[i]];
        return result;
    }
}
