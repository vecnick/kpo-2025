package hse.studying.zoo.domains;

import hse.studying.zoo.params.ThingParams;
import lombok.ToString;

/**
 * Class that represents a table in zoo.
 * A table is a thing.
 *
 * @see Thing
 */
@ToString(callSuper = true)
public class Table extends Thing {
    public Table(ThingParams params) {
        super(params);
    }
}
