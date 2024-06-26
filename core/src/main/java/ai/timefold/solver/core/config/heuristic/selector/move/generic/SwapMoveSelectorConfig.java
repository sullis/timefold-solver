package ai.timefold.solver.core.config.heuristic.selector.move.generic;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlType;

import ai.timefold.solver.core.config.heuristic.selector.common.nearby.NearbySelectionConfig;
import ai.timefold.solver.core.config.heuristic.selector.entity.EntitySelectorConfig;
import ai.timefold.solver.core.config.heuristic.selector.move.NearbyAutoConfigurationMoveSelectorConfig;
import ai.timefold.solver.core.config.util.ConfigUtils;
import ai.timefold.solver.core.impl.heuristic.selector.common.nearby.NearbyDistanceMeter;

@XmlType(propOrder = {
        "entitySelectorConfig",
        "secondaryEntitySelectorConfig",
        "variableNameIncludeList"
})
public class SwapMoveSelectorConfig extends NearbyAutoConfigurationMoveSelectorConfig<SwapMoveSelectorConfig> {

    public static final String XML_ELEMENT_NAME = "swapMoveSelector";

    @XmlElement(name = "entitySelector")
    private EntitySelectorConfig entitySelectorConfig = null;
    @XmlElement(name = "secondaryEntitySelector")
    private EntitySelectorConfig secondaryEntitySelectorConfig = null;

    @XmlElementWrapper(name = "variableNameIncludes")
    @XmlElement(name = "variableNameInclude")
    private List<String> variableNameIncludeList = null;

    public EntitySelectorConfig getEntitySelectorConfig() {
        return entitySelectorConfig;
    }

    public void setEntitySelectorConfig(EntitySelectorConfig entitySelectorConfig) {
        this.entitySelectorConfig = entitySelectorConfig;
    }

    public EntitySelectorConfig getSecondaryEntitySelectorConfig() {
        return secondaryEntitySelectorConfig;
    }

    public void setSecondaryEntitySelectorConfig(EntitySelectorConfig secondaryEntitySelectorConfig) {
        this.secondaryEntitySelectorConfig = secondaryEntitySelectorConfig;
    }

    public List<String> getVariableNameIncludeList() {
        return variableNameIncludeList;
    }

    public void setVariableNameIncludeList(List<String> variableNameIncludeList) {
        this.variableNameIncludeList = variableNameIncludeList;
    }

    // ************************************************************************
    // With methods
    // ************************************************************************

    public SwapMoveSelectorConfig withEntitySelectorConfig(EntitySelectorConfig entitySelectorConfig) {
        this.setEntitySelectorConfig(entitySelectorConfig);
        return this;
    }

    public SwapMoveSelectorConfig withSecondaryEntitySelectorConfig(EntitySelectorConfig secondaryEntitySelectorConfig) {
        this.setSecondaryEntitySelectorConfig(secondaryEntitySelectorConfig);
        return this;
    }

    public SwapMoveSelectorConfig withVariableNameIncludes(String... variableNameIncludes) {
        this.setVariableNameIncludeList(Arrays.asList(variableNameIncludes));
        return this;
    }

    // ************************************************************************
    // Builder methods
    // ************************************************************************

    @Override
    public SwapMoveSelectorConfig inherit(SwapMoveSelectorConfig inheritedConfig) {
        super.inherit(inheritedConfig);
        entitySelectorConfig = ConfigUtils.inheritConfig(entitySelectorConfig, inheritedConfig.getEntitySelectorConfig());
        secondaryEntitySelectorConfig = ConfigUtils.inheritConfig(secondaryEntitySelectorConfig,
                inheritedConfig.getSecondaryEntitySelectorConfig());
        variableNameIncludeList = ConfigUtils.inheritMergeableListProperty(
                variableNameIncludeList, inheritedConfig.getVariableNameIncludeList());
        return this;
    }

    @Override
    public SwapMoveSelectorConfig copyConfig() {
        return new SwapMoveSelectorConfig().inherit(this);
    }

    @Override
    public void visitReferencedClasses(Consumer<Class<?>> classVisitor) {
        visitCommonReferencedClasses(classVisitor);
        if (entitySelectorConfig != null) {
            entitySelectorConfig.visitReferencedClasses(classVisitor);
        }
        if (secondaryEntitySelectorConfig != null) {
            secondaryEntitySelectorConfig.visitReferencedClasses(classVisitor);
        }
    }

    @Override
    public SwapMoveSelectorConfig enableNearbySelection(Class<? extends NearbyDistanceMeter<?, ?>> distanceMeter,
            Random random) {
        SwapMoveSelectorConfig nearbyConfig = copyConfig();
        EntitySelectorConfig entityConfig = nearbyConfig.getEntitySelectorConfig();
        if (entityConfig == null) {
            entityConfig = new EntitySelectorConfig();
        }
        String entitySelectorId = addRandomSuffix("entitySelector", random);
        entityConfig.withId(entitySelectorId);
        EntitySelectorConfig secondaryConfig = nearbyConfig.getSecondaryEntitySelectorConfig();
        if (secondaryConfig == null) {
            secondaryConfig = new EntitySelectorConfig();
        }
        secondaryConfig.withNearbySelectionConfig(new NearbySelectionConfig()
                .withOriginEntitySelectorConfig(new EntitySelectorConfig()
                        .withMimicSelectorRef(entitySelectorId))
                .withNearbyDistanceMeterClass(distanceMeter));
        nearbyConfig.withEntitySelectorConfig(entityConfig)
                .withSecondaryEntitySelectorConfig(secondaryConfig);
        return nearbyConfig;
    }

    @Override
    public boolean hasNearbySelectionConfig() {
        return (entitySelectorConfig != null && entitySelectorConfig.hasNearbySelectionConfig())
                || (secondaryEntitySelectorConfig != null && secondaryEntitySelectorConfig.hasNearbySelectionConfig());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + entitySelectorConfig
                + (secondaryEntitySelectorConfig == null ? "" : ", " + secondaryEntitySelectorConfig) + ")";
    }

}
