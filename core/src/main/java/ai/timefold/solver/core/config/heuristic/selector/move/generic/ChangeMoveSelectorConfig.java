package ai.timefold.solver.core.config.heuristic.selector.move.generic;

import java.util.Random;
import java.util.function.Consumer;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import ai.timefold.solver.core.config.heuristic.selector.common.nearby.NearbySelectionConfig;
import ai.timefold.solver.core.config.heuristic.selector.entity.EntitySelectorConfig;
import ai.timefold.solver.core.config.heuristic.selector.move.NearbyAutoConfigurationMoveSelectorConfig;
import ai.timefold.solver.core.config.heuristic.selector.value.ValueSelectorConfig;
import ai.timefold.solver.core.config.util.ConfigUtils;
import ai.timefold.solver.core.impl.heuristic.selector.common.nearby.NearbyDistanceMeter;

@XmlType(propOrder = {
        "entitySelectorConfig",
        "valueSelectorConfig"
})
public class ChangeMoveSelectorConfig extends NearbyAutoConfigurationMoveSelectorConfig<ChangeMoveSelectorConfig> {

    public static final String XML_ELEMENT_NAME = "changeMoveSelector";

    @XmlElement(name = "entitySelector")
    private EntitySelectorConfig entitySelectorConfig = null;

    @XmlElement(name = "valueSelector")
    private ValueSelectorConfig valueSelectorConfig = null;

    public EntitySelectorConfig getEntitySelectorConfig() {
        return entitySelectorConfig;
    }

    public void setEntitySelectorConfig(EntitySelectorConfig entitySelectorConfig) {
        this.entitySelectorConfig = entitySelectorConfig;
    }

    public ValueSelectorConfig getValueSelectorConfig() {
        return valueSelectorConfig;
    }

    public void setValueSelectorConfig(ValueSelectorConfig valueSelectorConfig) {
        this.valueSelectorConfig = valueSelectorConfig;
    }

    // ************************************************************************
    // With methods
    // ************************************************************************

    public ChangeMoveSelectorConfig withEntitySelectorConfig(EntitySelectorConfig entitySelectorConfig) {
        this.setEntitySelectorConfig(entitySelectorConfig);
        return this;
    }

    public ChangeMoveSelectorConfig withValueSelectorConfig(ValueSelectorConfig valueSelectorConfig) {
        this.setValueSelectorConfig(valueSelectorConfig);
        return this;
    }

    // ************************************************************************
    // Builder methods
    // ************************************************************************

    @Override
    public ChangeMoveSelectorConfig inherit(ChangeMoveSelectorConfig inheritedConfig) {
        super.inherit(inheritedConfig);
        entitySelectorConfig = ConfigUtils.inheritConfig(entitySelectorConfig, inheritedConfig.getEntitySelectorConfig());
        valueSelectorConfig = ConfigUtils.inheritConfig(valueSelectorConfig, inheritedConfig.getValueSelectorConfig());
        return this;
    }

    @Override
    public ChangeMoveSelectorConfig copyConfig() {
        return new ChangeMoveSelectorConfig().inherit(this);
    }

    @Override
    public void visitReferencedClasses(Consumer<Class<?>> classVisitor) {
        visitCommonReferencedClasses(classVisitor);
        if (entitySelectorConfig != null) {
            entitySelectorConfig.visitReferencedClasses(classVisitor);
        }
        if (valueSelectorConfig != null) {
            valueSelectorConfig.visitReferencedClasses(classVisitor);
        }
    }

    @Override
    public ChangeMoveSelectorConfig enableNearbySelection(Class<? extends NearbyDistanceMeter<?, ?>> distanceMeter,
            Random random) {
        ChangeMoveSelectorConfig nearbyConfig = copyConfig();
        EntitySelectorConfig entityConfig = nearbyConfig.getEntitySelectorConfig();
        if (entityConfig == null) {
            entityConfig = new EntitySelectorConfig();
        }
        String entitySelectorId = addRandomSuffix("entitySelector", random);
        entityConfig.withId(entitySelectorId);
        ValueSelectorConfig valueConfig = nearbyConfig.getValueSelectorConfig();
        if (valueConfig == null) {
            valueConfig = new ValueSelectorConfig();
        }
        valueConfig.withNearbySelectionConfig(
                new NearbySelectionConfig()
                        .withOriginEntitySelectorConfig(new EntitySelectorConfig()
                                .withMimicSelectorRef(entitySelectorId))
                        .withNearbyDistanceMeterClass(distanceMeter));
        nearbyConfig.withEntitySelectorConfig(entityConfig)
                .withValueSelectorConfig(valueConfig);
        return nearbyConfig;
    }

    @Override
    public boolean hasNearbySelectionConfig() {
        return (entitySelectorConfig != null && entitySelectorConfig.hasNearbySelectionConfig())
                || (valueSelectorConfig != null && valueSelectorConfig.hasNearbySelectionConfig());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + entitySelectorConfig + ", " + valueSelectorConfig + ")";
    }

}
