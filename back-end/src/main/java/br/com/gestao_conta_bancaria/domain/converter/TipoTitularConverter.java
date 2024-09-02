package br.com.gestao_conta_bancaria.domain.converter;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.com.gestao_conta_bancaria.domain.enumeration.TipoTitularEnum;

@Converter(autoApply = true)
public class TipoTitularConverter implements AttributeConverter<TipoTitularEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoTitularEnum status) {        
        return isNotEmpty(status) ? status.getCodigo() : null;
    }

    @Override
    public TipoTitularEnum convertToEntityAttribute(Integer codigo) {
        return isNotEmpty(codigo) ? TipoTitularEnum.getById(codigo) : null;
    }
}
