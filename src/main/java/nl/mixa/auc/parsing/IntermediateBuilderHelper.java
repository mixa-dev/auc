package nl.mixa.auc.parsing;

import nl.mixa.auc.model.intermediate.IntermediateScanItem;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class IntermediateBuilderHelper {

    public static <T> void inject(int i, List<Object> data, IntermediateScanItem item, Function<IntermediateScanItem, Consumer<T>> setter, Function<Object, T> caster) {
        Object value = data.get(i);
        T t;
        if (value == null) {
            t = null;
        } else {
            t = caster.apply(value);
        }
        setter.apply(item).accept(t);
    }


    public static class IntermediateScanHelperBuilder<T> {
        private int i;
        private List<Object> data;
        private IntermediateScanItem item;
        private Function<IntermediateScanItem, Consumer<T>> setter;
        private Function<Object, T> caster;

        public IntermediateScanHelperBuilder() {
        }

        public IntermediateScanHelperBuilder<T> i(int i) {
            this.i = i;
            return this;
        }

        public IntermediateScanHelperBuilder<T> data(List<Object> data) {
            this.data = data;
            return this;
        }

        public IntermediateScanHelperBuilder<T> item(IntermediateScanItem item) {
            this.item = item;
            return this;
        }

        public IntermediateScanHelperBuilder<T> setter(Function<Object, T> caster, Function<IntermediateScanItem, Consumer<T>> setter) {
            this.caster = caster;
            this.setter = setter;
            return this;
        }

        public void build() {
            IntermediateBuilderHelper.<T>inject(i, data, item, setter, caster);
        }

        public String toString() {
            return "IntermediateBuilderHelper.IntermediateScanHelperBuilder(i=" + this.i + ", data=" + this.data + ", item=" + this.item + ", setter=" + this.setter + ", caster=" + this.caster + ")";
        }
    }
}
