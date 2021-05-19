package org.o7planning.sqlite.Adapter;

import android.widget.ArrayAdapter;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.o7planning.sqlite.Model.Customer;
import android.view.LayoutInflater;
import android.view.View;
import java.util.List;
import org.o7planning.sqlite.R;
import android.content.Context;


public class CustomAdapter extends ArrayAdapter<Customer> {
    private Context context;
    private int resoure;
    private List<Customer> listCustomer;

    public CustomAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Customer> objects) {
        super(context, resource, objects);
        this.context= context;
        this.resoure=resource;
        this.listCustomer=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_customer,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.tvId = (TextView)convertView.findViewById(R.id.tv_id);
            viewHolder.tvName = (TextView)convertView.findViewById(R.id.tv_name);
            viewHolder.tvPhoneNumber = (TextView)convertView.findViewById(R.id.tv_phone_number);
            viewHolder.tvEmail  = (TextView)convertView.findViewById(R.id.tv_email);
            viewHolder.tvAddress = (TextView)convertView.findViewById(R.id.tv_address);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Customer customer = listCustomer.get(position);
        viewHolder.tvId.setText(String.valueOf(customer.getId()));
        viewHolder.tvName.setText(customer.getName());
        viewHolder.tvAddress.setText(customer.getAddress());
        viewHolder.tvEmail.setText(customer.getEmail());
        viewHolder.tvPhoneNumber.setText(customer.getNumber());

        return convertView;
    }

    public class ViewHolder{

        private TextView tvId;
        private TextView tvName;
        private TextView tvAddress;
        private TextView tvEmail;
        private TextView tvPhoneNumber;
    }
}
