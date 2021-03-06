package org.colorcoding.ibas.bobas.expression;

import org.colorcoding.ibas.bobas.db.DataConvert;

public class JudgmentExpressionEnum extends JudgmentExpression<Object> {

	private Object leftValue;

	@Override
	public Object getLeftValue() {
		return this.leftValue;
	}

	@Override
	public void setLeftValue(Object value) {
		this.leftValue = value;
	}

	private Object rightValue;

	@Override
	public Object getRightValue() {
		return this.rightValue;
	}

	@Override
	public void setRightValue(Object value) {
		this.rightValue = value;
	}

	@Override
	public boolean result() throws NotSupportOperationException {
		// 等
		if (this.getOperation() == JudmentOperation.EQUAL) {
			String tmpLeft = DataConvert.toString(this.getLeftValue());
			String tmpLeftDb = DataConvert.toDbValue(this.getLeftValue());
			String tmpRight = DataConvert.toString(this.getRightValue());
			String tmpRightDb = DataConvert.toDbValue(this.getRightValue());
			if (tmpLeft != null && tmpLeft.equals(tmpRight)) {
				return true;
			}
			if (tmpLeftDb != null && tmpLeftDb.equals(tmpRightDb)) {
				return true;
			}
			return false;
		}
		// 不等
		else if (this.getOperation() == JudmentOperation.NOT_EQUAL) {
			String tmpLeft = DataConvert.toString(this.getLeftValue());
			String tmpLeftDb = DataConvert.toDbValue(this.getLeftValue());
			String tmpRight = DataConvert.toString(this.getRightValue());
			String tmpRightDb = DataConvert.toDbValue(this.getRightValue());
			if (tmpLeft != null && tmpLeft.equals(tmpRight)) {
				return false;
			}
			if (tmpLeftDb != null && tmpLeftDb.equals(tmpRightDb)) {
				return false;
			}
			return true;
		}
		// 空值
		else if (this.getOperation() == JudmentOperation.IS_NULL) {
			if (this.getLeftValue() == null) {
				return true;
			}
			return false;
		}
		// 非空值
		else if (this.getOperation() == JudmentOperation.NOT_NULL) {
			if (this.getLeftValue() != null) {
				return false;
			}
			return true;
		}
		return super.result();
	}
}
