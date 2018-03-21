package ADHomework.ADUtils;

import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvBadConverterException;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Basically a copy with few tunings: header and a bug fix for https://sourceforge.net/p/opencsv/bugs/168/ in a private method
 *
 */
public class ADMappingStrategy<T> extends ColumnPositionMappingStrategy<T> {

    public void setHeader(String[] head){
        header = head;
    }

    @Override
    public String[] generateHeader() {
        return header;
    }

    @Override
    protected void loadFieldMap() throws CsvBadConverterException {
        boolean required;
        fieldMap = new HashMap<>();

        for (Field field : loadFields(getType())) {
            String columnName;
            String fieldLocale;

            // Custom converters always have precedence.
            if (field.isAnnotationPresent(CsvCustomBindByPosition.class)) {
                columnName = field.getName().toUpperCase().trim();
                CsvCustomBindByPosition annotation = field
                        .getAnnotation(CsvCustomBindByPosition.class);
                Class<? extends AbstractBeanField> converter = annotation.converter();
                BeanField bean = instantiateCustomConverter(converter);
                bean.setField(field);
                required = annotation.required();
                bean.setRequired(required);
                fieldMap.put(columnName, bean);
            }

            // Then it must be a bind by position.
            else {
                CsvBindByPosition annotation = field.getAnnotation(CsvBindByPosition.class);
                required = annotation.required();
                columnName = field.getName().toUpperCase().trim();
                fieldLocale = annotation.locale();
                if (field.isAnnotationPresent(CsvDate.class)) {
                    String formatString = field.getAnnotation(CsvDate.class).value();
                    fieldMap.put(columnName, new BeanFieldDate(field, required, formatString, fieldLocale, errorLocale));
                } else {
                    fieldMap.put(columnName, new BeanFieldPrimitiveTypes(field, required, fieldLocale, errorLocale));
                }
            }
        }
    }

    private List<Field> loadFields(Class<? extends T> cls) {
        List<Field> fields = new ArrayList<>();
        for (Field field : FieldUtils.getAllFields(cls)) {
            if (field.isAnnotationPresent(CsvBindByPosition.class)
                    || field.isAnnotationPresent(CsvCustomBindByPosition.class)) {
                fields.add(field);
            }
        }
        annotationDriven = !fields.isEmpty();
        return fields;
    }
}
