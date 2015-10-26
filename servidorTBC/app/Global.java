import com.avaje.ebean.Model;
import play.*;
import play.libs.*;
import com.avaje.ebean.Ebean;
import models.*;
import java.util.*;
import utils.*;


public class Global extends GlobalSettings {
    @Override
    public void onStart(Application app) {
    	DummyData.loadDemoData();
    }
}
