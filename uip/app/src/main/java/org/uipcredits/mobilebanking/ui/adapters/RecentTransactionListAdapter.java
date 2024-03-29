package org.uip.mobilebanking.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.uip.mobilebanking.R;
import org.uip.mobilebanking.injection.ActivityContext;
import org.uip.mobilebanking.models.Transaction;
import org.uip.mobilebanking.utils.CurrencyUtil;
import org.uip.mobilebanking.utils.DateHelper;
import org.uip.mobilebanking.utils.Utils;



import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Vishwajeet
 * @since 10/08/16
 */
public class RecentTransactionListAdapter extends
        RecyclerView.Adapter<RecentTransactionListAdapter.ViewHolder> {

    private List<Transaction> transactions;
    private Context context;

    @Inject
    public RecentTransactionListAdapter(@ActivityContext Context context) {
        transactions = new ArrayList<>();
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_recent_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Transaction transaction = getItem(position);

        holder.tvAmount.setText(context.getString(R.string.string_and_string, transaction.
                getCurrency().getDisplaySymbol(), CurrencyUtil.formatCurrency(context,
                transaction.getAmount())));
        holder.tvTypeValue.setText(Utils.formatTransactionType(transaction.getType().getValue()));
        holder.tvTransactionsDate.setText(DateHelper.getDateAsString(transaction.
                getSubmittedOnDate()));
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
        notifyDataSetChanged();
    }


    public Transaction getItem(int position) {
        return transactions.get(position);
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_transactionDate)
        TextView tvTransactionsDate;

        @BindView(R.id.tv_amount)
        TextView tvAmount;

        @BindView(R.id.tv_value)
        TextView tvTypeValue;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}