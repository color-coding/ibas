package org.colorcoding.ibas.bobas.data.measurement;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.colorcoding.ibas.bobas.MyConfiguration;
import org.colorcoding.ibas.bobas.data.DataConvert;
import org.colorcoding.ibas.bobas.data.Decimal;

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "MeasurementDecimal", namespace = MyConfiguration.NAMESPACE_BOBAS_DATA)
public abstract class MeasurementDecimal<U> extends Measurement<BigDecimal, U> {

	private static final long serialVersionUID = 6473479770614930990L;

	public MeasurementDecimal() {

	}

	public MeasurementDecimal(BigDecimal value) {
		this();
		this.setValue(value);
	}

	public MeasurementDecimal(double value) {
		this();
		this.setValue(value);
	}

	@Override
	public double doubleValue() {
		return DataConvert.toDouble(this.getValue());
	}

	@Override
	public float floatValue() {
		return DataConvert.toFloat(this.getValue());
	}

	@Override
	public int intValue() {
		return DataConvert.toInt(this.getValue());
	}

	@Override
	public long longValue() {
		return DataConvert.toLong(this.getValue());
	}

	private BigDecimal _value;

	@Override
	@XmlElement(name = "Value")
	public BigDecimal getValue() {
		if (this._value == null) {
			this._value = Decimal.ZERO;
		}
		return this._value;
	}

	@Override
	public void setValue(Object value) {
		this.setValue((BigDecimal) value);
	}

	public void setValue(BigDecimal value) {
		BigDecimal oldValue = this._value;
		this._value = value;
		this.firePropertyChange("Value", oldValue, this._value);
	}

	@Override
	public void setValue(String value) {
		this.setValue(Decimal.valueOf(value));
	}

	@Override
	public void setValue(int value) {
		this.setValue(Decimal.valueOf(value));
	}

	@Override
	public void setValue(double value) {
		this.setValue(Decimal.valueOf(value));
	}

	@Override
	public void setValue(long value) {
		this.setValue(Decimal.valueOf(value));
	}

}
