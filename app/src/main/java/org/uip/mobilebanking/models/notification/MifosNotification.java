package org.uip.mobilebanking.models.notification;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import org.uip.mobilebanking.api.local.SelfServiceDatabase;


/**
 * Created by dilpreet on 13/9/17.
 */
@Table(database = SelfServiceDatabase.class)
public class MifosNotification extends BaseModel {

    @PrimaryKey
    long pk;

    @Column
    String timeStamp;

    @Column
    String msg;

    @Column
    Boolean read;

//    public MifosNotification(Long tstamp, String message, Boolean isRead){
//        this.setMsg(message);
//        this.setTimeStamp(tstamp);
//        this.setRead(isRead);
//    }

    public long getPk() {
        return pk;
    }

    public void setPk(long pk) {
        this.pk = pk;
    }
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
