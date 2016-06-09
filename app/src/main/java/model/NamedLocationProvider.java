package model;

import java.util.Collection;

/**
 * @author Hendrik Werner
 * @author Jasper Haasdijk
 * @author Janne van Den Hout
 * @author Arne Diehl
 */
public interface NamedLocationProvider {

    Collection<NamedLocation> getLocations();

}
