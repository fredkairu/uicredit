package org.uip.mobilebanking.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;

import org.uip.mobilebanking.R;


/**
 * Created by Oclemmy on 4/23/2016 for ProgrammingWizards Channel.
 */
public class MyHolder extends RecyclerView.ViewHolder {

    public TextView nameTxt;
    TextView brief;
    ImageView imageView;
    MaterialRippleLayout lyt_parent;
    public String price, propertyName;
    public MyHolder(View itemView) {
        super(itemView);
        nameTxt= (TextView) itemView.findViewById(R.id.nameTxt);
        brief= (TextView) itemView.findViewById(R.id.brief);
        imageView= (ImageView) itemView.findViewById(R.id.image);
        lyt_parent = (MaterialRippleLayout) itemView.findViewById(R.id.lyt_parent);
    }

    public View getView(){
        return this.itemView;
    }
}
