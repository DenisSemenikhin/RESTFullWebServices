package com.epam.WebClientRF.visual;

import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

import javax.xml.datatype.DatatypeConfigurationException;

import com.epam.WebClientRF.control.Start;
import com.epam.WebClientRF.model.FlyTicket;
import com.epam.WebClientRF.model.Human;
import com.epam.WebClientRF.model.Utilits;

public class ConsoleView {

	private Start client;

	public ConsoleView(Start client) {
		this.client = client;
	}

	public void showMainMenu() throws ParseException, DatatypeConfigurationException, ParseException, IOException {
		System.out.println("Главное меню");
		System.out.println("=============");
		System.out.println("Выберите действие");
		System.out.println("1. Забронировать билет");
		System.out.println("2. Получить билет по номеру");
		System.out.println("3. Оплатить билет");
		System.out.println("4. Вернуть билет");
		System.out.println("5. Выход из программы");
		System.out.println("Выберите вариант и нажмите Enter");
		Scanner in = new Scanner(System.in);
		String answer = in.nextLine();
		switch (answer) {
		case "1":
			if (client.getHuman() == null) {
				enterPersonalData();
			} else {
				showChangePersonalDataMenu();
			}
			showBookFlyTicketMenu();
			showTicket();
			showExitMainMenu();
			break;
		case "2":
			showGetTicketByIDMenu();
			showTicket();
			showExitMainMenu();
			break;
		case "3":
			showPaidForTicketByIDMenu();
			showExitMainMenu();
			break;
		case "4":
			showDeleteFlyTicketByIDMenu();
			showExitMainMenu();
			break;
		case "5":
			System.out.println("Спасибо за использование сервиса");
			break;
		default:
			showExitMainMenu();
			break;
		}

	}

	private void showBookFlyTicketMenu() throws IOException, ParseException {
		System.out.println("Бронирование билета");
		System.out.println("===================");
		System.out.println("MSK-VD-001 Moscow-Vladivostok");
		System.out.println("VD-MSK-002 Vladivostok-Moscow");
		System.out.println("Введите номер (пример \"VD-MSK-002\") рейса и нажмите Enter");
		Scanner in = new Scanner(System.in);
		String flyID = in.nextLine();
		Integer flyTicketID = client.bookFlyTicket(flyID);
		if (flyTicketID != -1) {
			System.out.println("Билет забронирован");
		} else {
			System.out.println("Билет не может быть забронирован");
			showBookFlyTicketMenu();
		}
	}

	private void showGetTicketByIDMenu() throws IOException, ParseException, DatatypeConfigurationException {
		System.out.println("Получение билета по номеру");
		System.out.println("==========================");
		System.out.println("Введите номер билета и нажмите Enter");
		Scanner in = new Scanner(System.in);
		String flyTicketID = in.nextLine();
		FlyTicket flyTicket = client.getFlyTicketByID(flyTicketID);
		if (flyTicket == null) {
			System.out.println("Билет по данному номеру не найден");
			showGetTicketByIDMenu();
		}
	}

	private void showPaidForTicketByIDMenu() throws ParseException, DatatypeConfigurationException, IOException {
		System.out.println("Оплата билета по номеру");
		System.out.println("==========================");
		System.out.println("Введите номер билета и нажмите Enter");
		Scanner in = new Scanner(System.in);
		String flyTicketID = in.nextLine();
		boolean paidFor = client.paidForTicketByID(flyTicketID);
		if (paidFor) {
			System.out.println("Билет успешно оплачен");
			showTicket();
		} else {
			System.out.println("Билет не оплачен");
		}
	}

	private void showDeleteFlyTicketByIDMenu() throws ParseException {
		System.out.println("Удаление билета по номеру");
		System.out.println("=========================");
		System.out.println("Введите номер билета и нажмите Enter");
		Scanner in = new Scanner(System.in);
		String flyTicketID = in.nextLine();
		boolean deleteStatus = client.deleteFlyTicketByID(flyTicketID);
		if (deleteStatus) {
			System.out.println("Билет успешно удален");
		} else {
			System.out.println("Билет не удален т.к. он имеет статус оплачен или такого билета нет");
		}
	}

	private void enterPersonalData() throws ParseException, DatatypeConfigurationException {
		System.out.println("Для работы с сервисом необходимо ввести персональные данные");
		Scanner in = new Scanner(System.in);
		System.out.print("Введите имя: ");
		String fName = in.nextLine();
		System.out.print("Введите фамилию: ");
		String lName = in.nextLine();
		System.out.print("Введите отчество: ");
		String patronomyc = in.nextLine();
		System.out.print("Введите дату рождения (образец \"2004-02-23\"): ");
		String birthdate = in.nextLine();
		System.out.println();
		client.acceptPersonalInformation(fName, lName, patronomyc, birthdate);
	}

	private void showTicket() throws DatatypeConfigurationException {
		System.out.print("Номер билета ");
		System.out.println(client.getTicket().getTicketNumber());
		System.out.print("Город отправления: ");
		System.out.println(client.getTicket().getDepartureCity());
		System.out.print("Дата отправления: ");
		System.out.println(client.getTicket().getDepartureDate());
		System.out.print("Город прибытия: ");
		System.out.println(client.getTicket().getArrivalCity());
		System.out.print("Дата прибытия: ");
		System.out.println(client.getTicket().getArrivalDate());
		Human human = client.getTicket().getHuman();
		System.out.print("Приобретатель: ");
		System.out.print(human.getfName() + " ");
		System.out.print(human.getPatronomyc() + " ");
		System.out.println(human.getlName() + " ");
		System.out.print("Дата рождения: ");
		Date birthDate = human.getBirthDate();
		String birthDateString = Utilits.datetoString(birthDate, Utilits.BIRTH_DATE_FORMAT);
		System.out.println(birthDateString);
		System.out.print("Статус билета: ");
		System.out.println(client.getTicket().getTicketStatus());
		System.out.print("Стоимость билета: ");
		BigInteger ammount = client.getTicket().getTicketCost().getAmount();
		String currencyLiter = client.getTicket().getTicketCost().getCurrencyLiter();
		System.out.println(ammount + " " + currencyLiter);
	}

	private void showExitMainMenu() throws DatatypeConfigurationException, IOException, ParseException, ParseException {
		System.out.println("Выход в главное меню Да(Д)/Нет(Н) ?");
		Scanner in = new Scanner(System.in);
		String answer = in.nextLine();
		switch (answer) {
		case "y":
		case "Y":
		case "д":
		case "Д":
			this.showMainMenu();
			break;
		case "N":
			System.out.println("Спасибо за использование сервиса");
			break;
		case "n":
			System.out.println("Спасибо за использование сервиса");
			break;
		case "Н":
			System.out.println("Спасибо за использование сервиса");
			break;
		case "н":
			System.out.println("Спасибо за использование сервиса");
			break;
		}
	}

	private void showChangePersonalDataMenu() throws DatatypeConfigurationException, ParseException {
		System.out.println("Используются следующие персональные данные: ");
		System.out.print(client.getHuman().getfName() + " ");
		System.out.print(client.getHuman().getPatronomyc() + " ");
		System.out.print(client.getHuman().getlName() + " ");
		System.out.print("Дата рождения: ");
		Date birthDate = client.getHuman().getBirthDate();
		String birthDateString = Utilits.datetoString(birthDate, Utilits.BIRTH_DATE_FORMAT);
		System.out.println(birthDateString);
		System.out.println("Желаете изменить? (Д)Да/(H)Нет");
		Scanner in = new Scanner(System.in);
		String answer = in.nextLine();
		switch (answer) {
		case "д":
		case "Д":
		case "y":
		case "Y":
			this.enterPersonalData();
		case "н":
		case "Н":
		case "N":
		case "n":
			break;
		default:
			break;
		}

	}
}
