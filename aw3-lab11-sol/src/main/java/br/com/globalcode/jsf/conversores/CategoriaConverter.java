package br.com.globalcode.jsf.conversores;

import br.com.globalcode.dao.jdbc.CategoriaJDBC;
import br.com.globalcode.model.Categoria;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Categoria.class)
public class CategoriaConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Categoria cat;
        try{
            cat = CategoriaJDBC.getInstance().getByPrimaryKey(Integer.parseInt(value));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConverterException(new FacesMessage("Erro ao converter Categoria com id " + value));
        }
        return cat;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Categoria cat = (Categoria) value;
        return cat.getId()+"";
    }
    
}
