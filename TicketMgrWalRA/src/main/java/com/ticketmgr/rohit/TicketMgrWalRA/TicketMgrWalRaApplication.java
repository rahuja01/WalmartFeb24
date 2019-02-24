package com.ticketmgr.rohit.TicketMgrWalRA;

import com.ticketmgr.rohit.Utility.EmailValidator;
import com.ticketmgr.rohit.model.SeatHold;
import com.ticketmgr.rohit.repository.impl.RepositoryImpl;
import com.ticketmgr.rohit.service.impl.TicketServiceImpl;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.util.Scanner;

@SpringBootApplication
public class TicketMgrWalRaApplication {

    static TicketServiceImpl ticketService = null;
    static Scanner scanner;

    public static void main(String[] args) {
        //SpringApplication.run(TicketMgrWalRaApplication.class, args);

        boolean flag = true;

        scanner = new Scanner(System.in);
        System.out.print("Enter the Total number of seats for the TicketMgt System ");
        String numOfSeats = scanner.nextLine();

        RepositoryImpl venuRep = new RepositoryImpl(Integer.parseInt(numOfSeats));
        ticketService = new TicketServiceImpl(venuRep);


            //System.out.println("Enter 1 to view total available seats OR 2 to hold seats OR 3 to allocate seats OR 4 to Quit ");
            //String choice = scanner.nextLine();
            int choiceSel = 1;

            try {
                //choiceSel = Integer.parseInt(choice);


                while(choiceSel!=0){
                    System.out.println("Enter 1 to view total available seats OR 2 to hold seats OR 3 to allocate seats OR 4 to Quit ");

                    String choice = scanner.nextLine();
                    choiceSel = Integer.parseInt(choice);

                    if (choiceSel < 1 || choiceSel > 3) {
                        System.out.println("Incorrect Value entered");

                    }

                    if(choiceSel==1){
                        printTotalSeatsAvailable();
                    }else if(choiceSel==2){
                        holdSeats();
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            /*switch (choiceSel) {
                case 0:
                    printTotalSeatsAvailable();
                case 1:
                    holdSeats();
                case 4:
                    break;

            }*/




            //flag = false;

            scanner.close();





    }

    public static void printTotalSeatsAvailable() {
        System.out.println("\nAvailable seats: " + ticketService.numSeatsAvailable() + "\n");
    }

    public static void holdSeats() {

        EmailValidator emailValidator = new EmailValidator();

        System.out.println("###### Please provide your email address for holding the tickets #######");

        String email = scanner.nextLine();
        if(!emailValidator.validate(email))
            System.out.println(" %%%%%%  Incorrect email address format, Please enter as per format, example - abc@gmail.com %%%%");
        while (emailValidator.validate(email)) {

            System.out.println("### Thanks your email address is validated ###");

            int numOfSeatsToHold = 0;
            while (numOfSeatsToHold == 0) {
                System.out.print("### Enter the number of seats to hold ### ");
                String SeatsToHold = scanner.nextLine();

                try {
                    SeatHold seatHold = ticketService.findAndHoldSeats(Integer.parseInt(SeatsToHold), email);
                    if (seatHold == null) {
                        numOfSeatsToHold = 0;
                        continue;
                    }

                    System.out.println("\n Seat hold id is " + seatHold.getId() + " with email "
                            + seatHold.getCustomerEmail() + "\n");

                } catch (Exception exception) {
                    exception.printStackTrace();
                    continue;
                }
            }

        }


    }


}
