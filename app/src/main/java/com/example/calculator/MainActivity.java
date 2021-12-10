package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity
{
    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnAdd, btnMinus, btnMultiply, btnDivide, btnClear, btnEqual, btnDelete, btnDecimal;
    private TextView tvInput, tvAnswer;
    private StringBuffer userInput = new StringBuffer("");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        initializeOperationListeners();

        tvInput.setText("Calculator");

        btn0.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                btnClick("0");
            }
        });

        btn1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                btnClick("1");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                btnClick("2");
            }
        });

        btn3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                btnClick("3");
            }
        });

        btn4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                btnClick("4");
            }
        });

        btn5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                btnClick("5");
            }
        });

        btn6.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                btnClick("6");
            }
        });

        btn7.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                btnClick("7");
            }
        });

        btn8.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                btnClick("8");
            }
        });

        btn9.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                btnClick("9");
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                userInput = new StringBuffer("");
                tvInput.setText("Calculator");
                tvAnswer.setText("");
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                System.out.println("Error");

                try
                {
                    userInput.setLength(userInput.length() - 1);
                    if(userInput.toString().equals(""))
                    {
                        userInput.append("");
                        tvInput.setText("Calculator");
                    }
                }
                catch (Exception e)
                {

                }
                tvInput.setText(userInput.toString());
            }
        });

        btnDecimal.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                btnClick(".");
            }
        });
    }

    public void initializeOperationListeners()
    {
        btnAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                doOperation("+");
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                doOperation("-");
            }
        });

        btnMultiply.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                doOperation("*");
            }
        });

        btnDivide.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                doOperation("/");
            }
        });

        btnEqual.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                nonPEMDAS();
            }
        });
    }

    public void initialize()
    {
        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btnAdd = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        btnMultiply = findViewById(R.id.btnMultiply);
        btnDivide = findViewById(R.id.btnDelete2);
        btnClear = findViewById(R.id.btnClear);
        btnEqual = findViewById(R.id.btnEqual);
        tvInput = findViewById(R.id.tvResult);
        tvAnswer = findViewById(R.id.tvAnswer);
        btnDelete = findViewById(R.id.btnDel);
        btnDecimal = findViewById(R.id.btnDecimal);
    }

    public void doOperation(String op)
    {
        userInput.trimToSize();
        userInput.append(" ").append(op).append(" ");
        tvInput.setText(userInput.toString());
    }

    public void btnClick(String num)
    {
        userInput.append(num);
        tvInput.setText(userInput.toString());
    }

    public void nonPEMDAS()
    {
        try
        {
            List<String> listOp = new ArrayList<String>();
            StringTokenizer tokenizer = new StringTokenizer(userInput.toString(), " ");
            while(tokenizer.hasMoreTokens())
            {
                listOp.add(tokenizer.nextToken());
            }

            if(listOp.size() == 0)
            {
                tvAnswer.setText("");
                return;
            }

            //check my first/last number
            try
            {
                double lastNum = Double.parseDouble(listOp.get(listOp.size() - 1));
                double firstNum = Double.parseDouble(listOp.get(0));
            }
            catch(NumberFormatException e)
            {
                tvAnswer.setText("ERROR");
                return;
            }

            double answer = 0.0;
            int i = 0;
            while(listOp.size() > 1)
            {
                double num1 = Double.parseDouble(listOp.get(i));
                double num2 = Double.parseDouble(listOp.get(i + 2));

                if(listOp.get(i + 1).equals("+"))
                {
                    answer = num1 + num2;
                }
                else if(listOp.get(i + 1).equals("-"))
                {
                    answer = num1 - num2;
                }
                else if(listOp.get(i + 1).equals("*"))
                {
                    answer = num1 * num2;
                }
                else
                {
                    try
                    {
                        answer = num1 / num2;
                    }
                    catch (Exception e)
                    {
                        tvAnswer.setText("ERROR");
                        return;
                    }
                }

                for(int j = 0; j < 3; j++)
                {
                    listOp.remove(0);
                }
                listOp.add(0, String.valueOf(answer));
            }
            if(answer % 1 == 0)
            {
                tvAnswer.setText((int)answer + "");
            }
            else
            {
                tvAnswer.setText(answer + "");
            }
        }
        catch (Exception e)
        {
            tvAnswer.setText("ERROR");
        }
    }

    public void PEMDAS()
    {
        try
        {
            List<String> listOp = new ArrayList<String>();
            StringTokenizer tokenizer = new StringTokenizer(userInput.toString(), " ");
            while(tokenizer.hasMoreTokens())
            {
                listOp.add(tokenizer.nextToken());
            }

            //check my first/last number
            try
            {
                double lastNum = Double.parseDouble(listOp.get(listOp.size() - 1));
                double firstNum = Double.parseDouble(listOp.get(0));
            }
            catch(NumberFormatException e)
            {
                tvAnswer.setText("ERROR");
                return;
            }

            double answer = 0.0;
            int i = 0;
            String operators[] = {"*", "/", "+", "-"};

            int j = 0;
            while(listOp.contains(operators[j]))
            {
                if(listOp.get(i).equals(operators[j]))
                {
                    double num1 = Double.parseDouble(listOp.get(i - 1));
                    double num2 = Double.parseDouble(listOp.get(i + 1));

                    if(operators[j].equals("*"))
                    {
                        answer = num1 * num2;
                    }
                    else if(operators[j].equals("/"))
                    {
                        try
                        {
                            answer = num1 / num2;
                        }
                        catch (Exception e)
                        {
                            tvAnswer.setText("ERROR");
                            return;
                        }
                    }
                    else if(operators[j].equals("+"))
                    {
                        answer = num1 + num2;
                    }
                    else
                    {
                        answer = num1 - num2;
                    }
                    listOp.remove(i - 1);
                    listOp.remove(i - 1);
                    listOp.remove(i - 1);
                    listOp.add(i - 1, String.valueOf(answer));

                    if(!listOp.get(i).equals(operators[j]))
                    {
                        j++;
                    }
                }
                i++;
            }
            if(answer % 1 == 0)
            {
                tvAnswer.setText((int)answer + "");
            }
            else
            {
                tvAnswer.setText(answer + "");
            }
        }
        catch (Exception e)
        {
            tvAnswer.setText("ERROR");
        }
    }
}