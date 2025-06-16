package com.example.myapplication.AvicaPatient.VivaLink.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.myapplication.AvicaPatient.R;
import com.vivalnk.sdk.model.DeviceModel;

/**
 * Created by JakeMo on 18-5-2.
 */
public class DeviceViewHolder extends RecyclerView.ViewHolder {

  public TextView tvDeviceName;
  public TextView tvRSSI;
  public TextView tvDeviceMac;
  public View cardView;
  public TextView tvConnected;

  public DeviceViewHolder(View itemView) {
    super(itemView);

    tvDeviceName = itemView.findViewById(R.id.tvDeviceName);
    tvRSSI = itemView.findViewById(R.id.tvRSSI);
    tvDeviceMac = itemView.findViewById(R.id.tvDeviceMac);
    cardView = itemView.findViewById(R.id.cardView);
    tvConnected = itemView.findViewById(R.id.tvConnected);
  }

  public void bind(final int position, final ScanListAdapter.StatusDevice item,
                   final OnItemClickListener listener) {

    String deviceName = item.device.getName();
    if (item.device.getModel() == DeviceModel.Checkme_O2) {
      if (item.device.getSn() != null && item.connect) {
        deviceName = deviceName + "(" + item.device.getSn().substring(item.device.getSn().length() - 4) + ")";
      }
    }

    tvDeviceName.setText(deviceName);
    tvRSSI.setText(String.valueOf(item.device.getRssi()));
    tvDeviceMac.setText(item.device.getId());
    tvConnected.setVisibility(item.connect ? View.VISIBLE : View.GONE);

    cardView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        listener.onItemClick(v, position, item.device);
      }
    });
  }
}
