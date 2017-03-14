package org.colorcoding.ibas.bobas.serialization.jersey.test;

import java.io.StringWriter;

import javax.xml.bind.JAXBException;

import org.colorcoding.ibas.bobas.bo.IBusinessObject;
import org.colorcoding.ibas.bobas.data.DateTime;
import org.colorcoding.ibas.bobas.data.Decimal;
import org.colorcoding.ibas.bobas.data.emBOStatus;
import org.colorcoding.ibas.bobas.data.emDocumentStatus;
import org.colorcoding.ibas.bobas.data.emYesNo;
import org.colorcoding.ibas.bobas.data.measurement.Time;
import org.colorcoding.ibas.bobas.data.measurement.emTimeUnit;
import org.colorcoding.ibas.bobas.mapping.DbFieldType;
import org.colorcoding.ibas.bobas.serialization.ISerializer;
import org.colorcoding.ibas.bobas.serialization.SerializerFactory;
import org.colorcoding.ibas.bobas.serialization.ValidateException;
import org.colorcoding.ibas.bobas.serialization.jersey.SerializerJson;
import org.colorcoding.ibas.bobas.test.bo.ISalesOrderItem;
import org.colorcoding.ibas.bobas.test.bo.SalesOrder;
import org.colorcoding.ibas.bobas.test.bo.User;

import junit.framework.TestCase;

public class testBusinessObject extends TestCase {

	public void testJson() throws JAXBException {
		SalesOrder order = new SalesOrder();
		order.setDocEntry(1);
		order.setCustomerCode("C00001");
		order.setDeliveryDate(DateTime.getToday());
		order.setDocumentStatus(emDocumentStatus.RELEASED);
		order.setDocumentTotal(new Decimal("99.99"));
		order.setDocumentUser(new User());
		// order.setTeamUsers(new User[] { new User(), new User() });
		order.setCycle(new Time(1.05, emTimeUnit.HOUR));
		order.getCycle().setValue(0.9988);

		order.getUserFields().addUserField("U_OrderType", DbFieldType.ALPHANUMERIC);
		order.getUserFields().addUserField("U_OrderId", DbFieldType.NUMERIC);
		order.getUserFields().addUserField("U_OrderDate", DbFieldType.DATE);
		order.getUserFields().addUserField("U_OrderTotal", DbFieldType.DECIMAL);
		// 注册自定义字段
		order.getUserFields().register();

		order.getUserFields().setValue("U_OrderType", "S0000");
		order.getUserFields().setValue("U_OrderId", 5768);
		order.getUserFields().setValue("U_OrderDate", DateTime.getToday());
		order.getUserFields().setValue("U_OrderTotal", new Decimal("999.888"));

		ISalesOrderItem orderItem = order.getSalesOrderItems().create();
		orderItem.setItemCode("A00001");
		orderItem.setQuantity(new Decimal(10));
		orderItem.setPrice(new Decimal(99.99));
		orderItem = order.getSalesOrderItems().create();
		orderItem.setItemCode("A00002");
		orderItem.setQuantity(10);
		orderItem.setPrice(199.99);

		String json = order.toString("json");
		System.out.println(json);
		ISerializer serializer = new SerializerJson();
		order = serializer.deserialize(json, order.getClass());
		String jsonNew = order.toString("json");
		System.out.println(jsonNew);
		// 此处会报错，因为自定义字段
		assertEquals(json, jsonNew);
	}

	public void testSerializStatus() throws ValidateException {
		// 测试反序列化的状态变化
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("{\"SalesOrder\":{");
		stringBuilder.append("\"SalesOrderItems\":[");
		stringBuilder.append("{");
		stringBuilder.append(String.format("\"LineStatus\":\"%s\",", emDocumentStatus.CLOSED));
		stringBuilder.append(String.format("\"Status\":\"%s\",", emBOStatus.CLOSED));
		stringBuilder.append(String.format("\"Canceled\":\"%s\"", emYesNo.YES));
		stringBuilder.append("},");
		stringBuilder.append("{");
		stringBuilder.append(String.format("\"LineStatus\":\"%s\",", emDocumentStatus.CLOSED));
		stringBuilder.append(String.format("\"Status\":\"%s\",", emBOStatus.CLOSED));
		stringBuilder.append(String.format("\"Canceled\":\"%s\"", emYesNo.YES));
		stringBuilder.append("},");
		stringBuilder.append("{");
		stringBuilder.append(String.format("\"LineStatus\":\"%s\",", emDocumentStatus.CLOSED));
		stringBuilder.append(String.format("\"Status\":\"%s\",", emBOStatus.CLOSED));
		stringBuilder.append(String.format("\"Canceled\":\"%s\"", emYesNo.YES));
		stringBuilder.append("}");
		stringBuilder.append("],");
		stringBuilder.append(String.format("\"DocumentStatus\":\"%s\",", emDocumentStatus.CLOSED));
		stringBuilder.append(String.format("\"Canceled\":\"%s\",", emYesNo.YES));
		stringBuilder.append(String.format("\"Status\":\"%s\"", emBOStatus.CLOSED));
		stringBuilder.append("}}");
		ISerializer serializer = new SerializerJson();
		serializer.validate(SalesOrder.class, stringBuilder.toString());
		IBusinessObject bo = serializer.deserialize(stringBuilder.toString(), SalesOrder.class);
		StringWriter writer = new StringWriter();
		serializer.serialize(bo, writer, true);
		System.out.println(writer.toString());
	}

	public void testJsonSchema() throws ValidateException {
		SalesOrder order = new SalesOrder();
		order.setDocEntry(1);
		order.setCustomerCode("C00001");
		order.setDeliveryDate(DateTime.getToday());
		order.setDocumentStatus(emDocumentStatus.RELEASED);
		order.setDocumentTotal(new Decimal("99.99"));
		order.setDocumentUser(new User());
		// order.setTeamUsers(new User[] { new User(), new User() });
		order.setCycle(new Time(1.05, emTimeUnit.HOUR));
		order.getCycle().setValue(0.9988);

		order.getUserFields().addUserField("U_OrderType", DbFieldType.ALPHANUMERIC);
		order.getUserFields().addUserField("U_OrderId", DbFieldType.NUMERIC);
		order.getUserFields().addUserField("U_OrderDate", DbFieldType.DATE);
		order.getUserFields().addUserField("U_OrderTotal", DbFieldType.DECIMAL);
		// 注册自定义字段
		order.getUserFields().register();

		order.getUserFields().setValue("U_OrderType", "S0000");
		order.getUserFields().setValue("U_OrderId", 5768);
		order.getUserFields().setValue("U_OrderDate", DateTime.getToday());
		order.getUserFields().setValue("U_OrderTotal", new Decimal("999.888"));

		ISalesOrderItem orderItem = order.getSalesOrderItems().create();
		orderItem.setItemCode("A00001");
		orderItem.setQuantity(new Decimal(10));
		orderItem.setPrice(new Decimal(99.99));
		orderItem = order.getSalesOrderItems().create();
		orderItem.setItemCode("A00002");
		orderItem.setQuantity(10);
		orderItem.setPrice(199.99);

		ISerializer serializer = SerializerFactory.create().createManager().create("json");
		StringWriter writer = new StringWriter();
		serializer.getSchema(order.getClass(), writer);
		System.out.println(writer.toString());
		serializer.validate(order.getClass(), order.toString("json"));
	}
}