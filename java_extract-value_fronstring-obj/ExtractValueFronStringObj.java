package com.telefonica.wprepaid;

import java.lang.reflect.Field;
import java.util.Objects;

import com.telefonica.wprepaid.dto.WelcomePrepaidDTO;
import com.telefonica.wprepaid.pojo.NotifyEsb;
import com.telefonica.wprepaid.pojo.SmsRequest;

public class ExtractValueFronStringObj {
	
	public static void main(String[] args){
		try {
			WelcomePrepaidDTO dto = new WelcomePrepaidDTO();
			dto.setStreamTrackingId("1");
			NotifyEsb notify = new NotifyEsb();
			notify.setEmail("mguarniz@email.com");
			dto.setNotifyEsb(notify);
			SmsRequest sms = new SmsRequest();
			sms.setPhoneNumber("975757409");
			
			notify.setSms(sms);
			
			String att = "notifyEsb.sms.phoneNumber";
//			String att = "notifyEsb.sms1.phoneNumber";
//			String att = "notifyEsb.sms.phoneNumber1";
//			String att = "notifyEsb.email";
//			String att = "streamTrackingId";
			
			Object value = getValueFromSequence(dto, att);
			System.out.println(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static Object getValueFromSequence(Object obj, String sequence) throws Exception{
		System.out.println("input -> " + sequence);
		try {
			if(sequence.contains(".")) {
				int indexPoint = sequence.indexOf(".");
				String attr = sequence.substring(0, indexPoint);
				String nextAttr = sequence.substring(indexPoint + 1);
				Field field = getFileFromAttName(obj, attr);
				return getValueFromSequence(field.get(obj), nextAttr);
			} else {
				Field field = getFileFromAttName(obj, sequence);
				return field.get(obj);
			}
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	private static Field getFileFromAttName(Object obj, String propName){
		
		try {
			Field field = obj.getClass().getDeclaredField(propName);
			field.setAccessible(true);
			return field;
		} catch (NullPointerException | NoSuchFieldException e) {
			return null;
		}
	}
	
}
