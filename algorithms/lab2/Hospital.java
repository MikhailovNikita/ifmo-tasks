

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 * Created by mikha on 15.11.2015.
 */
public class Hospital {
    public static class Patient {
        int number;
        Patient prev;
        Patient next;
        static Patient queueMid;
        static Patient head;
        static Patient tail;

        public Patient(int number, Patient next, Patient prev) {
            this.number = number;
            this.next = next;
            this.prev = prev;
        }
    }

    public static void main(String[] args) throws Exception {
        File inputFile = new File("hospital.in");
        File outputFile = new File("hospital.out");
        BufferedReader in = new BufferedReader(new FileReader(inputFile));
        PrintWriter out = new PrintWriter(outputFile);

        int n = Integer.parseInt(in.readLine());
        char op;
        int pat;
        String s;
        int queueSize = 0;
        s = in.readLine();
        op = s.charAt(0);
        pat = Integer.parseInt(s.substring(2));
        Patient patien = new Patient(pat, null, null);
        Patient.head = patien;
        Patient.tail = patien;
        Patient.queueMid = patien;
        queueSize++;

        for (int i = 1; i < n; i++) {
            s = in.readLine();
            op = s.charAt(0);
            System.out.println(i + " iteration");
            System.out.println();
            if (op == '+') {
                pat = Integer.parseInt(s.substring(2));
                if (queueSize == 0) {
                    Patient patient = new Patient(pat, null, null);
                    Patient.head = patient;
                    Patient.tail = patient;
                    Patient.queueMid = patient;
                    queueSize++;
                } else {
                    Patient patient = new Patient(pat, Patient.tail, null);
                    Patient.tail.prev = patient;
                    Patient.tail = patient;
                    if (queueSize % 2 == 0) {
                        Patient.queueMid = patient.queueMid.prev;
                    }
                    queueSize++;
                }


            }

            if (op == '-') {
                //System.out.println(Patient.head.number);
                out.println(Patient.head.number);
                Patient.head = Patient.head.prev;
                if (queueSize % 2 == 0) {
                    Patient.queueMid = Patient.queueMid.prev;
                }
                queueSize--;
            }
            if (op == '*') {
                pat = Integer.parseInt(s.substring(2));
                if (queueSize == 0) {
                    Patient patient = new Patient(pat, null, null);
                    Patient.head = patient;
                    Patient.tail = patient;
                    Patient.queueMid = patient;
                }
                if (queueSize == 1) {
                    Patient patient = new Patient(pat, Patient.queueMid, null);
                    Patient.queueMid.prev = patient;
                    Patient.tail=patient;
                }
                if (queueSize > 1) {
                    Patient patient = new Patient(pat, Patient.queueMid, Patient.queueMid.prev);
                    Patient.queueMid.prev.next = patient;
                    Patient.queueMid.prev = patient;
                    if (queueSize % 2 == 0) {
                        Patient.queueMid = patient;
                    }

                }
                queueSize++;
            }

        }
        out.close();
    }

}
