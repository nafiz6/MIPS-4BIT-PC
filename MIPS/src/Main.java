import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

class MIPSConverter
{
    private String [] instructions = {"j","beq","subi","addi","sub","add","lw","sw",
    "ori","sll","srl","bne","nor","and","or","andi"};

    private String [] registers = {"$t0","$t1","$t2","$t3","$t4"};

    public String getOpCode(String s)
    {
        for(int i=0; i<16; i++)
        {
            if(s.equals(instructions[i]))
            {
                return convertToBin(i,4);
            }
        }
        return null;
    }

    public String getRegister(String s)
    {
        if(s.equals("$zero")) return "1111";
        else
        {
            for(int i=0; i<5; i++)
            {
                if(s.equals(registers[i]))
                {
                    return convertToBin(i,4);
                }
            }
        }
        return null;
    }

    public String convertToBin(int i,int bit)
    {
        String code = "";
        String opCode = Integer.toBinaryString(i);
        if(opCode.length()<bit)
        {
            int diff = bit - opCode.length();
            for(int j=0; j<diff; j++) code = code + "0";

        }

        code = code + opCode;
        return code;
    }

    public String convert(String S)
    {
        String code = "";

        String [] input = S.split(" ");
        String s = input[0];
        String inst = "";
        for (int i=1; i<input.length; i++)
            inst = inst + input[i];

        if(s.equals(instructions[0]))
        {
            //J
            code = code + getOpCode(s) + convertToBin(Integer.parseInt(inst),8) + "00000000";
            return code;

        }
        else if(s.equals(instructions[1]))
        {
            //BEQ
            String [] reg = inst.split(",");
            code = code + getOpCode(s) + getRegister(reg[0]) + getRegister(reg[1]) + convertToBin(Integer.parseInt(reg[2]),8);
            return code;
        }
        else if(s.equals(instructions[2]))
        {
            //SUBI
            String [] reg = inst.split(",");
            code = code + getOpCode(s) + getRegister(reg[1]) + getRegister(reg[0]) + convertToBin(Integer.parseInt(reg[2]),8);
            return code;

        }
        else if(s.equals(instructions[3]))
        {
            //ADDI
            String [] reg = inst.split(",");
            code = code + getOpCode(s) + getRegister(reg[1]) + getRegister(reg[0]) + convertToBin(Integer.parseInt(reg[2]),8);
            return code;

        }
        else if(s.equals(instructions[4]))
        {
            //SUB
            String [] reg = inst.split(",");
            code = code + getOpCode(s) + getRegister(reg[1]) + getRegister(reg[2]) + getRegister(reg[0]) + "0000";
            return code;
        }
        else if(s.equals(instructions[5]))
        {
            //ADD
            String [] reg = inst.split(",");
            code = code + getOpCode(s) + getRegister(reg[1]) + getRegister(reg[2]) + getRegister(reg[0]) + "0000";
            return code;
        }
        else if(s.equals(instructions[6]))
        {
            //LW
            inst = inst.replace('(',',');
            inst = inst.replace(')',',');
            String [] reg = inst.split(",");

            code = code + getOpCode(s) + getRegister(reg[2]) + getRegister(reg[0]) + convertToBin(Integer.parseInt(reg[1]),8);
            return code;

        }
        else if(s.equals(instructions[7]))
        {
            //SW
            inst = inst.replace('(',',');
            inst = inst.replace(')',',');
            String [] reg = inst.split(",");

            code = code + getOpCode(s) + getRegister(reg[2]) + getRegister(reg[0]) + convertToBin(Integer.parseInt(reg[1]),8);
            return code;

        }
        else if(s.equals(instructions[8]))
        {
            //ORI
            String [] reg = inst.split(",");
            code = code + getOpCode(s) + getRegister(reg[1]) + getRegister(reg[0]) + convertToBin(Integer.parseInt(reg[2]),8);
            return code;

        }
        else if(s.equals(instructions[9]))
        {
            //SLL
            String [] reg = inst.split(",");
            code = code + getOpCode(s) + "0000" + getRegister(reg[1]) + getRegister(reg[0]) + convertToBin(Integer.parseInt(reg[2]),4);
            return code;
        }
        else if(s.equals(instructions[10]))
        {
            //SRL
            String [] reg = inst.split(",");
            code = code + getOpCode(s) + "0000" + getRegister(reg[1]) + getRegister(reg[0]) + convertToBin(Integer.parseInt(reg[2]),4);
            return code;
        }
        else if(s.equals(instructions[11]))
        {
            //BNE
            String [] reg = inst.split(",");
            code = code + getOpCode(s) + getRegister(reg[0]) + getRegister(reg[1]) + convertToBin(Integer.parseInt(reg[2]),8);
            return code;
        }
        else if(s.equals(instructions[12]))
        {
            //NOR
            String [] reg = inst.split(",");
            code = code + getOpCode(s) + getRegister(reg[1]) + getRegister(reg[2]) + getRegister(reg[0]) + "0000";
            return code;
        }
        else if(s.equals(instructions[13]))
        {
            //AND
            String [] reg = inst.split(",");
            code = code + getOpCode(s) + getRegister(reg[1]) + getRegister(reg[2]) + getRegister(reg[0]) + "0000";
            return code;
        }
        else if(s.equals(instructions[14]))
        {
            //OR
            String [] reg = inst.split(",");
            code = code + getOpCode(s) + getRegister(reg[1]) + getRegister(reg[2]) + getRegister(reg[0]) + "0000";
            return code;
        }
        else if(s.equals(instructions[15]))
        {
            //ANDI
            String [] reg = inst.split(",");
            code = code + getOpCode(s) + getRegister(reg[1]) + getRegister(reg[0]) + convertToBin(Integer.parseInt(reg[2]),8);
            return code;
        }

        return null;
    }
}


public class Main {

    public static void main(String[] args) {

        MIPSConverter C = new MIPSConverter();//final String input = "input.txt";
        int count = 1;
        final String input = "input.txt";
        final String output = "output.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(input));
            BufferedWriter bw = new BufferedWriter(new FileWriter(output));
            while(true)
            {
                String s = br.readLine();
                if (s==null)break;
                s = C.convert(s);
                System.out.print(count + ". ");
                System.out.println(s);
                count++;
                bw.write(s);

                bw.newLine();
            }
            bw.close();
            br.close();
        }
        catch(Exception e){}

    }
}