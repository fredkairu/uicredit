package org.uip.mobilebanking.models;

import com.raizlabs.android.dbflow.structure.BaseModel;

public class NotificationModel extends BaseModel {
        Long timeStamp;
        String msg;
        Boolean read;

        public NotificationModel(Long tstamp, String message, Boolean isRead){
            this.setMsg(message);
            this.setTimeStamp(tstamp);
            this.setRead(isRead);
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public long getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(long timeStamp) {
            this.timeStamp = timeStamp;
        }

        public boolean isRead() {
            return read;
        }

        public void setRead(boolean read) {
            this.read = read;
        }
}
