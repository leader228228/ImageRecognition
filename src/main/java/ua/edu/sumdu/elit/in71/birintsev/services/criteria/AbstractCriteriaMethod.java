package ua.edu.sumdu.elit.in71.birintsev.services.criteria;

import lombok.AllArgsConstructor;
import ua.edu.sumdu.elit.in71.birintsev.CriteriaValue;
import ua.edu.sumdu.elit.in71.birintsev.NeighbourClasses;
import ua.edu.sumdu.elit.in71.birintsev.services.ClassBitmapService;

@AllArgsConstructor
public abstract class AbstractCriteriaMethod implements CriteriaMethod {

    private final ClassBitmapService classBitmapService;

    @Override
    public CriteriaValue getFor(
        int radius,
        NeighbourClasses hypersphereNeighbourClasses,
        double minDistinguishPercentage
    ) {
        boolean[][] clazz =
            hypersphereNeighbourClasses
                .getClassBitmap()
                .getBitmap();
        boolean[][] neighbour =
            hypersphereNeighbourClasses
                .getNeighbourClassBitmap()
                .getBitmap();
        double d1 = classBitmapService.belongPercent(
            clazz,
            hypersphereNeighbourClasses.getClassBitmap(),
            radius
        );
        double d2 = classBitmapService.notBelongPercent(
            neighbour,
            hypersphereNeighbourClasses.getClassBitmap(),
            radius
        );
        double criteria = calc(d1, d2);
        return new CriteriaValue(
            radius,
            hypersphereNeighbourClasses,
            criteria,
            minDistinguishPercentage,
            belongsToWorkspace(d1, d2, minDistinguishPercentage),
            this
        );
    }

    /**
     * Counts the criteria value
     *
     * @param d1 a percentage of <strong>current</strong> class
     *           implementations that belong
     *           to current class
     *           with current radius
     * @param d2 a percentage of <strong>neighbour</strong> class
     *           implementations that <strong>do not</strong> belong
     *           to current class
     *           with current radius
     * */
    public abstract double calc(double d1, double d2);

    private boolean belongsToWorkspace(
        double d1,
        double d2,
        double minDistinguishPercentage
    ) {
        return d1 >= minDistinguishPercentage
            && d2 >= minDistinguishPercentage;
    }


}
