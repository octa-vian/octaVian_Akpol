package gmedia.net.id.OnTime;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import gmedia.net.id.OnTime.utils.ApiVolley;
import gmedia.net.id.OnTime.utils.LinkURL;
import gmedia.net.id.OnTime.utils.Proses;
import gmedia.net.id.OnTime.utils.SessionManager;

public class Login extends AppCompatActivity {
	private Button btnLogin;
	private SessionManager session;
	private EditText username, password;
	private Proses proses;
	private RelativeLayout btnNjajal;
	private String id_perusahaan, message;
	private EditText isian, txtEmail;
	private TextView tv_tbn_psw;
	public static Boolean isIDPerusahaan;
	private boolean onback = false;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		proses = new Proses(Login.this);
		session = new SessionManager(Login.this);
		session.checkLogin();

//		session.createLoginSession();
		/*id_perusahaan = session.getKeyIdPerusahaan();
		if (id_perusahaan.equals("")) {
			popupInputIDPerusahaan();
		}*/

		tv_tbn_psw = (TextView) findViewById(R.id.tv_pswd);
		tv_tbn_psw.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View ps) {
				final Dialog dialog = new Dialog(Login.this);
				dialog.setContentView(R.layout.popup_reset_pswd);
				dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
				txtEmail = (EditText) dialog.findViewById(R.id.txt_email);
				RelativeLayout btnClose = (RelativeLayout) dialog.findViewById(R.id.btnClosePopupIDPerusahaan);
				RelativeLayout btn_kirim = (RelativeLayout) dialog.findViewById(R.id.btnKirim);
				btnClose.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						onback = false;
						dialog.dismiss();
					}
				});

				btn_kirim.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						kirimData();
						dialog.dismiss();
					}
				});

				dialog.show();
				/*Intent ps2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://stackoverflow.com/questions/24261224/android-open-url-onclick-certain-button"));
				startActivity(ps2);*/
			}
		});
		initUI();
		initAction();
	}



	@Override
	protected void onResume() {
		super.onResume();
		session.checkIDPerusahaan();
		if (!isIDPerusahaan) {
			if (!onback) {
				//popupInputIDPerusahaan();
			}
		}
	}

	private void initUI() {
		btnLogin = (Button) findViewById(R.id.btnLogin);
		username = (EditText) findViewById(R.id.ETusername);
		password = (EditText) findViewById(R.id.ETpassword);
		btnNjajal = (RelativeLayout) findViewById(R.id.btnNjajal);
	}

	private void kirimData() {

		JSONObject body = new JSONObject();
		try {
			body.put("email", txtEmail.getText().toString());
		}
		catch (JSONException e) {
			e.printStackTrace();
		}

		ApiVolley request = new ApiVolley(Login.this, body, "POST", LinkURL.RePassword, "", "", 0, new ApiVolley.VolleyCallback() {
			@Override
			public void onSuccess(String result) {
				proses.DismissDialog();
				Log.d("Response", result);
				try {
					JSONObject object = new JSONObject(result);
					int status = object.getJSONObject("metadata").getInt("status");
					String message;
					if (status==200){
						final Dialog dialog = new Dialog(Login.this);
						dialog.setContentView(R.layout.popup_sukses2);
						dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
						RelativeLayout btnOk = (RelativeLayout) dialog.findViewById(R.id.btnOkPopupSukses);
						btnOk.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								dialog.dismiss();
								Intent intent = new Intent(Login.this, Login.class);
								((Activity) Login.this).startActivity(intent);
								((Activity) Login.this).overridePendingTransition(R.anim.no_move, R.anim.fade_out_animation);
								((Activity) Login.this).finish();
							}
						});
						dialog.show();
						message = object.getJSONObject("metadata").getString("message");
					} else {
						message = object.getJSONObject("metadata").getString("message");
					}
					Log.d("OnSuccses", message);
				} catch (JSONException e) {
					e.printStackTrace();
					Log.d("response", e.getMessage());
				}

			}
			@Override
			public void onError(String result) {
				Log.d("Error.Response", result);
			}

		});

		}

	private void initAction() {
		/*btnNjajal.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

			}
		});*/
		btnLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (username.getText().toString().equals("")) {
					username.setError("Mohon Di Isi");
					username.requestFocus();
					return;
				} else if (password.getText().toString().equals("")) {
					password.setError("Mohon Di Isi");
					password.requestFocus();
					return;
				} else {
					proses.ShowDialog();

					final JSONObject jBody = new JSONObject();
					try {
						jBody.put("username", username.getText().toString());
						jBody.put("password", password.getText().toString());
					} catch (JSONException e) {
						e.printStackTrace();
					}
					ApiVolley request = new ApiVolley(Login.this, jBody, "POST", LinkURL.UrlLogin, "", "", 0, new ApiVolley.VolleyCallback() {
						@Override
						public void onSuccess(String result) {
							proses.DismissDialog();
							try {
								JSONObject object = new JSONObject(result);
								String status = object.getJSONObject("metadata").getString("status");
								message = object.getJSONObject("metadata").getString("message");
								if (status.equals("200")) {
									String user_id = object.getJSONObject("response").getString("username");
									String token = object.getJSONObject("response").getString("token");
									String id_karyawan = object.getJSONObject("response").getString("id_karyawan");
									String profile_name = object.getJSONObject("response").getString("profile_name");
									String id_user = object.getJSONObject("response").getString("id_user");
									String approvalCuti = object.getJSONObject("response").getString("cuti");
									String approvalIjin = object.getJSONObject("response").getString("ijin");
									session.createLoginSession(user_id, token, id_karyawan, profile_name, id_user, approvalCuti, approvalIjin);
									Intent intent = new Intent(Login.this, MainActivityBaru.class);

									/*"response": {
										"id_user": "2",
												"username": "iniesta",
												"id_karyawan": "71",
												"profile_name": "",
												"token": "$1$mTdzu6x3$VWDDUIZcNU4NrvmJAY8qk1",
												"cuti": "0",
												"ijin": "0"
									},*/
									startActivity(intent);
									finish();
								} else if (status.equals("401")) {
									final Dialog dialog = new Dialog(Login.this);
									dialog.setContentView(R.layout.popupincorectusernamepassword);
									RelativeLayout close = dialog.findViewById(R.id.closeincorectusernamepassword);
									close.setOnClickListener(new View.OnClickListener() {
										@Override
										public void onClick(View v) {
											dialog.dismiss();
										}
									});
									dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
									dialog.show();
									final Handler handler = new Handler();
									final Runnable runnable = new Runnable() {
										@Override
										public void run() {
											if (dialog.isShowing()) {
												dialog.dismiss();
											}
										}
									};

									dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
										@Override
										public void onDismiss(DialogInterface dialog) {
											handler.removeCallbacks(runnable);

										}
									});
									handler.postDelayed(runnable, 3000);

								} else if (status.equals("400")) {
//									Toast.makeText(Login.this, message, Toast.LENGTH_LONG).show();
									session.deleteIDPerusahaan();
									session.checkIDPerusahaan();
									if (!isIDPerusahaan) {
										if (!onback) {
											popupSalahIdPerusahaan();
										}
									}
								} else if (status.equals("404")) {
									popupHarusIsiIdPerusahaan();
								} else {
									Toast.makeText(Login.this, message, Toast.LENGTH_LONG).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

						@Override
						public void onError(String result) {
							proses.DismissDialog();
							Toast.makeText(Login.this, "terjadi kesalahan", Toast.LENGTH_LONG).show();
						}
					});
				}
			}
		});
	}

	private void popupHarusIsiIdPerusahaan() {
		final Dialog dialog = new Dialog(Login.this);
		dialog.setContentView(R.layout.popup_harus_isi_id_perusahaan);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		RelativeLayout btnClose = (RelativeLayout) dialog.findViewById(R.id.btnClosePopupHarusIsiIDPerusahaan);
		btnClose.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialog.dismiss();
				if (!isIDPerusahaan) {
					if (!onback) {
						//popupInputIDPerusahaan();
					}
				}
			}
		});
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		final Handler handler = new Handler();
		final Runnable runnable = new Runnable() {
			@Override
			public void run() {
				if (dialog.isShowing()) {
					dialog.dismiss();

				}
			}
		};

		dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				handler.removeCallbacks(runnable);
				if (!isIDPerusahaan) {
					if (!onback) {
						//popupInputIDPerusahaan();
					}
				}
			}
		});
		handler.postDelayed(runnable, 3000);
	}

	private void popupSalahIdPerusahaan() {
		final Dialog dialog = new Dialog(Login.this);
		dialog.setContentView(R.layout.popup_id_perusahaan_salah);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		TextView txtError = (TextView) dialog.findViewById(R.id.txtErrorIDPerusahaan);
		txtError.setText(message);
		RelativeLayout btnClose = (RelativeLayout) dialog.findViewById(R.id.btnClosePopupIDPerusahaanSalah);
		btnClose.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialog.dismiss();
				if (!isIDPerusahaan) {
					if (!onback) {
						//popupInputIDPerusahaan();
					}
				}
			}
		});
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		final Handler handler = new Handler();
		final Runnable runnable = new Runnable() {
			@Override
			public void run() {
				if (dialog.isShowing()) {
					dialog.dismiss();
				}
			}
		};

		dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				handler.removeCallbacks(runnable);
				if (!isIDPerusahaan) {
					if (!onback) {
						//popupInputIDPerusahaan();
					}
				}
			}
		});
		handler.postDelayed(runnable, 3000);
	}

	/*private void popupInputIDPerusahaan() {
		onPopupIDPerusahaan = true;
//		id_perusahaan = session.getKeyIdPerusahaan();
		final Dialog dialog = new Dialog(Login.this);
		dialog.setContentView(R.layout.popup_isi_id_perusahaan);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		isian = (EditText) dialog.findViewById(R.id.isianPopupIDPerusahaan);
		RelativeLayout btnClose = (RelativeLayout) dialog.findViewById(R.id.btnClosePopupIDPerusahaan);
		btnClose.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onPopupIDPerusahaan = false;
				dialog.dismiss();
			}
		});
		RelativeLayout btnVerifikasi = (RelativeLayout) dialog.findViewById(R.id.btnVerifikasiPopupIDPerusahaan);
		btnVerifikasi.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (isian.getText().toString().equals("")) {
					isian.setError("Mohon Di Isi Terlebih Dahulu");
					isian.requestFocus();
					return;
				} else {
					id_perusahaan = isian.getText().toString();
					session.createLoginSessionIDPerusahaan(id_perusahaan);
					dialog.dismiss();
					onPopupIDPerusahaan = false;
				}
			}
		});
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		dialog.setCancelable(false);
		*//*dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					onPopupIDPerusahaan = false;
					dialog.dismiss();
				}
				return true;
			}
		});*//*

	}*/
}
