package org.o7planning.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import org.o7planning.sqlite.Model.Customer;
import org.o7planning.sqlite.Data.DatabaseManager;
import org.o7planning.sqlite.Adapter.CustomAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText edtName;
    private EditText editAddress;
    private EditText edtNumber;
    private EditText edtEmail;
    private EditText edtId;
    private Button btnSave;
    private Button btnUpdate;
    private ListView lvCustomer;
    private DatabaseManager dbManager;
    private CustomAdapter customAdapter;
    private List<Customer> customerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbManager = new DatabaseManager(this);
        initWidget();
        customerList = dbManager.getAllCustomer();
        setAdapter();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Customer customer = createCustomer();
                if (customer != null) {
                    dbManager.addCustomer(customer);
                }
                updateListStudent();
                setAdapter();
            }
        });
        lvCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Customer customer = customerList.get(position);
                edtId.setText(String.valueOf(customer.getId()));
                edtName.setText(customer.getName());
                editAddress.setText(customer.getAddress());
                edtEmail.setText(customer.getEmail());
                edtNumber.setText(customer.getNumber());
                btnSave.setEnabled(false);
                btnUpdate.setEnabled(true);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Customer customer = new Customer();
                customer.setId(Integer.parseInt(String.valueOf(edtId.getText())));
                customer.setName(edtName.getText()+"");
                customer.setAddress(editAddress.getText()+"");
                customer.setEmail(edtEmail.getText()+"");
                customer.setNumber(edtNumber.getText()+"");
                int result = dbManager.updateCustomer(customer);
                if(result>0){
                    updateListStudent();
                }
                btnSave.setEnabled(true);
                btnUpdate.setEnabled(false);
            }
        });
        lvCustomer.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                Customer customer = customerList.get(position);
                int result = dbManager.deleteCustomer(customer.getId());
                if(result>0){
                    Toast.makeText(MainActivity.this, "Delete successfully", Toast.LENGTH_SHORT).show();
                    updateListStudent();
                }else{
                    Toast.makeText(MainActivity.this, "Delete fail", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }

    private Customer createCustomer() {
        String name = edtName.getText().toString();
        String address = String.valueOf(editAddress.getText());
        String phoneNumber = edtNumber.getText() + "";
        String email = edtEmail.getText().toString();

        Customer customer = new Customer(name, address, phoneNumber, email);
        return customer;
    }

    private void initWidget() {
        edtName = (EditText) findViewById(R.id.edt_name);
        editAddress = (EditText) findViewById(R.id.edt_address);
        edtNumber = (EditText) findViewById(R.id.edt_number);
        edtEmail = (EditText) findViewById(R.id.edt_email);
        btnSave = (Button) findViewById(R.id.btn_save);
        lvCustomer = (ListView) findViewById(R.id.lv_student);
        edtId = (EditText) findViewById(R.id.edt_id);
        btnUpdate = (Button) findViewById(R.id.btn_update);
    }

    private void setAdapter() {
        if (customAdapter == null) {
            customAdapter = new CustomAdapter (this, R.layout.item_list_customer, customerList);
            lvCustomer.setAdapter(customAdapter);
        } else {
            customAdapter.notifyDataSetChanged();
            lvCustomer.setSelection(customAdapter.getCount() - 1);
        }
    }
    public void updateListStudent(){
        customerList.clear();
        customerList.addAll(dbManager.getAllCustomer());
        if(customAdapter!= null){
            customAdapter.notifyDataSetChanged();
        }
    }
}