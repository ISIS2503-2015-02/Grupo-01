import play.*;
import play.libs.*;
import models.*;
import java.util.*;
import utils.*;

public class Global extends GlobalSettings{
	@Override
	public void onStart(Application app){
		if(Usuario.find.all().isEmpty()){
			DummyData.loadDemoData();
			super.onStart(app);
		}
	}
}