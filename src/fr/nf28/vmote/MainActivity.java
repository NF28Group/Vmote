package fr.nf28.vmote;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;
import com.actionbarsherlock.view.SubMenu;

import fr.nf28.vmote.R;
import fr.nf28.vmote.history.view.HistoryViewPagerFragment;
import fr.nf28.vmote.interfaces.OnChangePageListener;
import fr.nf28.vmote.play.view.ViewPagerFragment;
import fr.nf28.vmote.series.view.SeriesHomeFragment;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends SherlockFragmentActivity implements OnChangePageListener {
	private boolean useLogo = false;
	public boolean isConnected = false;
	private boolean showHomeUp = false;
	private final static String TAG_FRAGMENT = "TAG_FRAGMENT";
	private Activity cxt;
	private PopupWindow popUp;
	
	private final String androidHelp =
    "<p>Vous devez entrer l\'adresse IP de l\'ordinateur auquel vous souhaiter vous connecter." +
    "Entrez cette adresse dans le menu \"trouver VLC\".</p>" +
    "<p>Pour connaître l\'adresse IP de votre ordinateur, ouvrez une console de commande" +
    "(sous Windows, allez dans la barre de programme et tapez \"cmd\", vous devrez ensuite\n" +
    "sélectionner le programme \"cmd.exe\" ou juste \"cmd_\").</p>" +
    "<p>Une fois la console ouverte, tapez \"ifconfig\" sous Linux/OS X ou \"ipconfig\" sous windows" +
    "puis valider.</p>" +
    "<p>Votre adresse IP se trouve à la ligne intitulée \"IPv4\"</p>" +
    "<p>C\'est une suite de chiffre de la forme suivante : X.X.X.X</p>" +
    "<p>Entrez cette adresse IP dans le champ indiqué et valider.</p>"
			;
	
	private final String desktopHelp = 
"<p>Pour faire fonctionner cette application avec votre logiciel VLC :</p>" +
		"<p>I. Il faut activer la fonctionnalité web de VLC :<br>" +
	         "1. ouvrez VLC<br>" +
	         "2. allez dans les préférences de VLC (CTRL + P) ou menu Outils->Préférences<br>" +
	         "3. affichez la liste complète des options du menu latéral gauche<br>" +
	         "4. sélectionnez le sous-menu interface<br>" +
	         "5. sélectionnez son sous-menu Interface Générale<br>" +
	         "6. cochez la case HTTP<br>" +
	         "7. écrivez \"http\" si ce n\'est pas automatique dans le champ de texte<br>" +
	         "8. sauvegardez<br>" +
	         "</p><p>" +
	       "II. Il faut autoriser votre appareil android à communiquer avec VLC :<br>" +
	       	"1. allez dans le répertoire d\'installation de votre lecteur VLC<br>" +
	       	"2. ...<br>" +
	      "</p>" +
	"<p>Félicitation, votre lecteur VLC est configuré pour fonctionner avec votre appareil VLC !</p>";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_layout);
		cxt = this;
		
		AbstractFragment fragment = new ViewPagerFragment();
		Bundle arguments = new Bundle();
		arguments.putString(ViewPagerFragment.ARG_ITEM_ID, "pager_play_fragment");
        fragment.setArguments(arguments);
        cxt.setTitle("Lecture");
		getSupportFragmentManager().beginTransaction()
			.replace(R.id.applicationview_detail_container, fragment, TAG_FRAGMENT)
			.addToBackStack(null)
			.commit();
	}


	@Override
	public void selectPage(int page) {
		// useless..
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		//Used to put dark icons on light action bar
		//boolean isLight = SampleList.THEME == R.style.Theme_Sherlock_Light;
		
		menu.add("Lecture")
		.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				AbstractFragment fragment = new ViewPagerFragment();
				Bundle arguments = new Bundle();
				arguments.putString(ViewPagerFragment.ARG_ITEM_ID, "pager_play_fragment");
		        fragment.setArguments(arguments);
		        cxt.setTitle("Lecture");
				getSupportFragmentManager().beginTransaction()
					.replace(R.id.applicationview_detail_container, fragment, TAG_FRAGMENT)
					.addToBackStack(null)
					.commit();
				
				activateMenuButton(menu, 0);
				return true;
			}
		})
		.setIcon(R.drawable.vmoteplay)
		//.setActionView(R.layout.tvseries_menu)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

		menu.add("Historique")
		.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				AbstractFragment fragment = new HistoryViewPagerFragment();
				Bundle arguments = new Bundle();
				arguments.putString(HistoryViewPagerFragment.ARG_ITEM_ID, "pager_history_fragment");
		        fragment.setArguments(arguments);
		        cxt.setTitle("Historique");
				getSupportFragmentManager().beginTransaction()
					.replace(R.id.applicationview_detail_container, fragment, TAG_FRAGMENT)
					.addToBackStack(null)
					.commit();
				
				activateMenuButton(menu, 1);
				return true;
			}
		})
		.setIcon(R.drawable.vmotehistory)
		//.setActionView(R.layout.tvseries_menu)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW); //SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		menu.add("Series")
		.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				AbstractFragment fragment = new SeriesHomeFragment();
				Bundle arguments = new Bundle();
				arguments.putString(SeriesHomeFragment.ARG_ITEM_ID, "serie_home_fragment");
		        fragment.setArguments(arguments);
		    	cxt.setTitle("Series");
				getSupportFragmentManager().beginTransaction()
					.replace(R.id.applicationview_detail_container, fragment, TAG_FRAGMENT)
					.addToBackStack(null)
					.commit();
				
				activateMenuButton(menu, 2);
				return true;
			}
			
		})
		.setIcon(R.drawable.vmotetvshow)
		//.setActionView(R.layout.tvseries_menu)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW); //SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		
		
		// menu parametres
		SubMenu param = menu.addSubMenu("Parametres");
		param.setIcon(R.drawable.abs__ic_menu_moreoverflow_normal_holo_dark);
		param.add(R.string.ipCheck);
		param.add(R.string.helpVLC);
		param.add(R.string.helpAndroid);
		param.add(R.string.information);

	    MenuItem subMenuItem = param.getItem();
		
	    param.getItem(0).setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				showIPView();
				return false;
			}
		});
	    
	    // aide VLC
	    param.getItem(1).setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				showHelpView(true);
				return false;
			}
		});
	    
	    // aide android
	    param.getItem(2).setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				showHelpView(false);
				return false;
			}
		});
	    
	    // info
	    param.getItem(3).setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				showInfoView();
				return false;
			}
		});
		
		subMenuItem
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		
		activateMenuButton(menu, 0);
		return true;
	}
	
	private void activateMenuButton(Menu menu, int index){
		switch(index){
			case 0:
				menu.getItem(0).setIcon(R.drawable.vmoteplayon);
				menu.getItem(1).setIcon(R.drawable.vmotehistory);
				menu.getItem(2).setIcon(R.drawable.vmotetvshow);
				break;
			
			case 1:
				menu.getItem(0).setIcon(R.drawable.vmoteplay);
				menu.getItem(1).setIcon(R.drawable.vmotehistoryon);
				menu.getItem(2).setIcon(R.drawable.vmotetvshow);
				break;
				
			case 2:
				menu.getItem(0).setIcon(R.drawable.vmoteplay);
				menu.getItem(1).setIcon(R.drawable.vmotehistory);
				menu.getItem(2).setIcon(R.drawable.vmotetvshowon);
				break;
			
			default:
			break;
		}
	
	}
	
	private void showHelpView(boolean isDesktop) {
		View internalLayout;
        TextView tv;
        Button btnOk;
		
		//We need to get the instance of the LayoutInflater, use the context of this activity
		LayoutInflater inflater = (LayoutInflater) MainActivity.this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//Inflate the view from a predefined XML layout
		if(isDesktop) {
			internalLayout = inflater.inflate(R.layout.settings_help_layout_desktop,
					(ViewGroup) findViewById(R.id.settings_help_layout_desktop));
		}
		else {
			internalLayout = inflater.inflate(R.layout.settings_help_layout_android,
					(ViewGroup) findViewById(R.id.settings_help_layout_android));
		}
		
        // create a 500px width and 570px height PopupWindow
		popUp = new PopupWindow(internalLayout, 500, 570, true);
		
		// display the popup in the center
        popUp.showAtLocation(internalLayout, Gravity.CENTER, 0, 0);
		
        if (isDesktop) {
        	tv = (TextView) internalLayout.findViewById(R.id.tvHelpDesk);
        	btnOk = (Button) internalLayout.findViewById(R.id.btnHelpDeskBack);
			tv.setText(Html.fromHtml(desktopHelp));
		}
        else {
			tv = (TextView) internalLayout.findViewById(R.id.tvHelpAnd);
        	btnOk = (Button) internalLayout.findViewById(R.id.btnHelpAndroidBack);

			tv.setText(Html.fromHtml(androidHelp));
        }
        
        btnOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popUp.dismiss();
			}
		});
	}
	
	private void showIPView() {
		Toast.makeText(cxt, "IP", Toast.LENGTH_SHORT).show();				
	}
	
	private void showInfoView() {
		Toast.makeText(cxt, "Info", Toast.LENGTH_SHORT).show();
	}
}
