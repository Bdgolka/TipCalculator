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

	// константы, используемые при сохранении/восстановлении состояния
	private static final String BILL_TOTAL = "BILL_TOTAL";
	private static final String CUSTOM_PERCENT = "CUSTOM_PERCENT";

	private double currentBillTotal; // счет, вводимый пользователем
	private int currentCustomPercent; // % чаевых, выбранный SeekBar

	private EditText tip10EditText; // 10%-чаевые
	private EditText total10EditText; // общий счет, включая 10%-чаевые
	private EditText tip15EditText; // 15%-чаевые
	private EditText total15EditText; // общий счет, включая 15%-чаевые
	private EditText billEditText; // ввод счета пользователем
	private EditText tip20EditText; // 20%-чаевые
	private EditText total20EditText; // общий счет, включая 20%-чаевые
	private TextView customTipTextView; // % пользовательских чаевых
	private EditText tipCustomEditText; // пользовательские чаевые
	private EditText totalCustomEditText; // общий счетс пользовательскими
											// чаевыми

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Приложение запущено впервые или восстановлено из памяти
		if (saveInstanceState == null) {
			currentBillTotal = 0.0; // инициализация суммы счета нулем
			currentCustomPercent = 18; // нициализация пользовательских чаевых
		} else {
			// инициализация суммы счета сохраненной в памяти суммой
			currentBillTotal = savedInstanceState.getDouble(BILL_TOTAL);

			// инициализация пользовательских чаевых сохраненным
			// процентом чаевых
			currentCustomPercent = savedInstanceState.getInt(CUSTOM_PERCENT);
		}

		// ссылки на чаевые 10 %, 15 %, 20 % и итоговые EditTexts
		tip10EditText = (EditText) findViewById(R.id.tip10EditText);
		total10EditText = (EditText) findViewById(R.id.total10EditText);
		tip15EditText = (EditText) findViewById(R.id.tip15EditText);
		total15EditText = (EditText) findViewById(R.id.total15EditText);
		tip20EditText = (EditText) findViewById(R.id.tip20EditText);
		total20EditText = (EditText) findViewById(R.id.total20EditText);

		// TextView, отображающий процент пользовательских чаевых
		customTipTextView = (TextView) findViewById(R.id.customTipTextView);

		// пользовательские чаевые и итоговые EditTexts
		tipCustomEditText = (EditText) findViewById(R.id.tipCustomEditText);
		totalCustomEditText = (EditText) findViewById(R.id.totalCustomEditText);

		// получение billEditText
		billEditText = (EditText) findViewById(R.id.billEditText);

		// billEditTextWatcher обрабатывает событие onTextChanged из
		// billingEditText
		billEditText.addTextChangedListener(billEditTextWatcher);

		// получение SeekBar, используемого для подсчета суммы
		// пользовательских чаевых
		SeekBar customSeekBar = (SeekBar) findViewById(R.id.customSeekBar);
		customSeekBar.setOnSeekBarChangeListener(customSeekBarListener);

	}

	// вычисляем чаевые, хранящиеся в EditText по ставкам 10, 15, 20 %

	private void updateStandard() {

		// вычисляем итоговый счет, ключающий чаевые со ставкой 10%
		double tenPercentTip = currentBillTotal * .1;
		double tenPercentTotal = currentBillTotal + tenPercentTip;

		// настройка текста tipTenEditText в соотсвествии с tenPercentTip
		tip10EditText.setText(String.format("%.02f", tenPercentTip));

		// настройка текста totalTenEditText в соответствии с tenPercentTotal
		total10EditText.setText(String.format("%.02f", tenPercentTotal));

		// вычисление общего итога с чаевыми 15 %
		double fifteenPercentTip = currentBillTotal * .15;
		double fifteenPercentTotal = currentBillTotal + fifteenPercentTip;

		// настройка текста tipTenEditText в соотсвествии с fifteenPercentTip
		tip15EditText.setText(String.format("%.02f", fifteenPercentTip));

		// настройка текста totalTenEditText в соответствии с
		// fifteenPercentTotal
		total15EditText.setText(String.format("%.02f", fifteenPercentTotal));

		// вычисление общего итога с чаевыми 20 %
		double twentyPercentTip = currentBillTotal * .20;
		double twentyPercentTotal = currentBillTotal + twentyPercentTip;

		// настройка текста tipTenEditText в соотсвествии с twentyPercentTip
		tip15EditText.setText(String.format("%.02f", twentyPercentTip));

		// настройка текста totalTenEditText в соответствии с twentyPercentTotal
		total15EditText.setText(String.format("%.02f", twentyPercentTotal));
	}

	// обновляет компоненты EditText, включающие пользовательские
	// чаевые и итоги

	private void updateCustom() {
		// Настройка текста customTextview в соответствии с положением SeekBar
		customTipTextView.setText(currentCustomPercent + "%");

		// вычисление суммы пользовательских чаевых
		double customTipAmount = currentBillTotal * currentCustomPercent * .01;

		// вычисление итогового счета, включая пользовательские чаевые
		double customTotalAmount = currentBillTotal + customTipAmount;

		// Итображаем сумму чаевых и итогового счета
		tipCustomEditText.setText(String.format("%.02f", customTotalAmount));
		totalCustomEditText.setText(String.format("%.02f", customTotalAmount));
	}

	// сохранение значений billEditText и customSeekBar
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putDouble(BILL_TOTAL, currentBillTotal);
		outState.putInt(CUSTOM_PERCENT, currentCustomPercent);
	}
	
	//вызываем при изменении пользователем положения ползунка SEekBar 
	private OnSeekBarChangeListener customSeekBarListener = new OnSeekBarChangeListener(){

		//Обносление currentCustomPercent, потом вызов updateCustom
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
