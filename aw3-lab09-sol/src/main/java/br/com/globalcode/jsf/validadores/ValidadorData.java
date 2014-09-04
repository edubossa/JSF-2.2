package br.com.globalcode.jsf.validadores;

import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("br.com.globalcode.jsf.ValidadorData")
public class ValidadorData implements Validator {

    public void validate(FacesContext ctx, UIComponent componente, Object valor) throws ValidatorException {
        Date dataLida = (Date) valor;
        if (dataLida.before(new Date())) {
            throw new ValidatorException(new FacesMessage("A data deve ser posterior à data de hoje"));
        }
    }

}
