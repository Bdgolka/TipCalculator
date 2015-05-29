package com.example.tipcalculator;

import android.app.Activity;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class TipCalculator extends Activity {

	// ���������, ������������ ��� ����������/�������������� ���������
	private static final String BILL_TOTAL = "BILL_TOTAL";
	private static final String CUSTOM_PERCENT = "CUSTOM_PERCENT";

	private double currentBillTotal; // ����, �������� �������������
	private int currentCustomPercent; // % ������, ��������� SeekBar

	private EditText tip10EditText; // 10%-������
	private EditText total10EditText; // ����� ����, ������� 10%-������
	private EditText tip15EditText; // 15%-������
	private EditText total15EditText; // ����� ����, ������� 15%-������
	private EditText billEditText; // ���� ����� �������������
	private EditText tip20EditText; // 20%-������
	private EditText total20EditText; // ����� ����, ������� 20%-������
	private TextView customTipTextView; // % ���������������� ������
	private EditText tipCustomEditText; // ���������������� ������
	private EditText totalCustomEditText; // ����� ����� �����������������
											// �������

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// ���������� �������� ������� ��� ������������� �� ������
		if (saveInstanceState == null) {
			currentBillTotal = 0.0; // ������������� ����� ����� �����
			currentCustomPercent = 18; // ������������ ���������������� ������
		} else {
			// ������������� ����� ����� ����������� � ������ ������
			currentBillTotal = savedInstanceState.getDouble(BILL_TOTAL);

			// ������������� ���������������� ������ �����������
			// ��������� ������
			currentCustomPercent = savedInstanceState.getInt(CUSTOM_PERCENT);
		}

		// ������ �� ������ 10 %, 15 %, 20 % � �������� EditTexts
		tip10EditText = (EditText) findViewById(R.id.tip10EditText);
		total10EditText = (EditText) findViewById(R.id.total10EditText);
		tip15EditText = (EditText) findViewById(R.id.tip15EditText);
		total15EditText = (EditText) findViewById(R.id.total15EditText);
		tip20EditText = (EditText) findViewById(R.id.tip20EditText);
		total20EditText = (EditText) findViewById(R.id.total20EditText);

		// TextView, ������������ ������� ���������������� ������
		customTipTextView = (TextView) findViewById(R.id.customTipTextView);

		// ���������������� ������ � �������� EditTexts
		tipCustomEditText = (EditText) findViewById(R.id.tipCustomEditText);
		totalCustomEditText = (EditText) findViewById(R.id.totalCustomEditText);

		// ��������� billEditText
		billEditText = (EditText) findViewById(R.id.billEditText);

		// billEditTextWatcher ������������ ������� onTextChanged ��
		// billingEditText
		billEditText.addTextChangedListener(billEditTextWatcher);

		// ��������� SeekBar, ������������� ��� �������� �����
		// ���������������� ������
		SeekBar customSeekBar = (SeekBar) findViewById(R.id.customSeekBar);
		customSeekBar.setOnSeekBarChangeListener(customSeekBarListener);

	}

	// ��������� ������, ���������� � EditText �� ������� 10, 15, 20 %

	private void updateStandard() {

		// ��������� �������� ����, ��������� ������ �� ������� 10%
		double tenPercentTip = currentBillTotal * .1;
		double tenPercentTotal = currentBillTotal + tenPercentTip;

		// ��������� ������ tipTenEditText � ������������ � tenPercentTip
		tip10EditText.setText(String.format("%.02f", tenPercentTip));

		// ��������� ������ totalTenEditText � ������������ � tenPercentTotal
		total10EditText.setText(String.format("%.02f", tenPercentTotal));

		// ���������� ������ ����� � ������� 15 %
		double fifteenPercentTip = currentBillTotal * .15;
		double fifteenPercentTotal = currentBillTotal + fifteenPercentTip;

		// ��������� ������ tipTenEditText � ������������ � fifteenPercentTip
		tip15EditText.setText(String.format("%.02f", fifteenPercentTip));

		// ��������� ������ totalTenEditText � ������������ �
		// fifteenPercentTotal
		total15EditText.setText(String.format("%.02f", fifteenPercentTotal));

		// ���������� ������ ����� � ������� 20 %
		double twentyPercentTip = currentBillTotal * .20;
		double twentyPercentTotal = currentBillTotal + twentyPercentTip;

		// ��������� ������ tipTenEditText � ������������ � twentyPercentTip
		tip15EditText.setText(String.format("%.02f", twentyPercentTip));

		// ��������� ������ totalTenEditText � ������������ � twentyPercentTotal
		total15EditText.setText(String.format("%.02f", twentyPercentTotal));
	}

	// ��������� ���������� EditText, ���������� ����������������
	// ������ � �����

	private void updateCustom() {
		// ��������� ������ customTextview � ������������ � ���������� SeekBar
		customTipTextView.setText(currentCustomPercent + "%");

		// ���������� ����� ���������������� ������
		double customTipAmount = currentBillTotal * currentCustomPercent * .01;

		// ���������� ��������� �����, ������� ���������������� ������
		double customTotalAmount = currentBillTotal + customTipAmount;

		// ���������� ����� ������ � ��������� �����
		tipCustomEditText.setText(String.format("%.02f", customTotalAmount));
		totalCustomEditText.setText(String.format("%.02f", customTotalAmount));
	}

	// ���������� �������� billEditText � customSeekBar
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putDouble(BILL_TOTAL, currentBillTotal);
		outState.putInt(CUSTOM_PERCENT, currentCustomPercent);
	}
	
	//�������� ��� ��������� ������������� ��������� �������� SEekBar 
	private OnSeekBarChangeListener customSeekBarListener = new OnSeekBarChangeListener(){

		//���������� currentCustomPercent, ����� ����� updateCustom
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			
			
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
		
	};
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tip_calculator, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
