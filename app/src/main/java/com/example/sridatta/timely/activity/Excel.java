package com.example.sridatta.timely.activity;
//sukrita's code
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sridatta.timely.Manifest;
import com.example.sridatta.timely.R;
import com.example.sridatta.timely.objects.LectureSlot;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import static com.google.android.gms.internal.zzben.NULL;

public class Excel extends AppCompatActivity {

    LectureSlot slot;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private static final String TAG = "TAG" ;

    private HashMap<String, String> day;
    private HashMap<String, String> hour;


    // Declare variables
    private String[] FilePathStrings;
    private String[] FileNameStrings;
    private File[] listFile;
    File file;

    Button btnUpDirectory,btnSDCard;

    ArrayList<String> pathHistory;
    String lastDirectory;
    int count = 0;



    ListView lvInternalStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel);
        toastMessage(" Browse excel file for your time table ");
        slot=new LectureSlot();



        //code begins here
        lvInternalStorage = (ListView) findViewById(R.id.lvInternalStorage);
        btnUpDirectory = (Button) findViewById(R.id.btnUpDirectory);
        btnSDCard = (Button) findViewById(R.id.btnViewSDCard);

        //need to check the permissions
        checkFilePermissions();
        toastMessage(" double tap the file to confirm");

        lvInternalStorage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lastDirectory = pathHistory.get(count);
                if(lastDirectory.equals(adapterView.getItemAtPosition(i))){
                    Log.d(TAG, "lvInternalStorage: Selected a file for upload: " + lastDirectory);
                    if(new File(lastDirectory).getName().endsWith(".xlsx"))
                        readExcelData(lastDirectory);
                    else
                        toastMessage("wrong file selected");

                }else
                {
                    count++;
                    pathHistory.add(count,(String) adapterView.getItemAtPosition(i));
                    checkInternalStorage();
                    Log.d(TAG, "lvInternalStorage: " + pathHistory.get(count));
                }
            }
        });

        //Goes up one directory level
        btnUpDirectory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count == 0){
                    Log.d(TAG, "btnUpDirectory: You have reached the highest level directory.");
                }else{
                    pathHistory.remove(count);
                    count--;
                    checkInternalStorage();
                    Log.d(TAG, "btnUpDirectory: " + pathHistory.get(count));
                }
            }
        });

        //Opens the SDCard or phone memory
        btnSDCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = 0;
                pathHistory = new ArrayList<String>();
                pathHistory.add(count,System.getenv("EXTERNAL_STORAGE"));
                Log.d(TAG, "btnSDCard: " + pathHistory.get(count));
                checkInternalStorage();
            }
        });

        // code ends here

    }
    private void readExcelData(String filePath) {
        Log.d(TAG, "readExcelData: Reading Excel File.");

        //decarle input file
        File inputFile = new File(filePath);

        try {
            InputStream inputStream = new FileInputStream(inputFile);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            updateTimetable(workbook);

        }catch (FileNotFoundException e) {
            Log.e(TAG, "readExcelData: FileNotFoundException. " + e.getMessage() );
        } catch (IOException e) {
            Log.e(TAG, "readExcelData: Error reading inputstream. " + e.getMessage() );
        }
    }

    private void checkInternalStorage() {
        Log.d(TAG, "checkInternalStorage: Started.");
        try{
            if (!Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                toastMessage("No SD card found.");
            }
            else{
                // Locate the image folder in your SD Car;d
                file = new File(pathHistory.get(count));
                Log.d(TAG, "checkInternalStorage: directory path: " + pathHistory.get(count));
            }

            listFile = file.listFiles();

            // Create a String array for FilePathStrings
            FilePathStrings = new String[listFile.length];

            // Create a String array for FileNameStrings
            FileNameStrings = new String[listFile.length];

            for (int i = 0; i < listFile.length; i++) {
                // Get the path of the image file
                FilePathStrings[i] = listFile[i].getAbsolutePath();
                // Get the name image file
                FileNameStrings[i] = listFile[i].getName();
            }

            for (int i = 0; i < listFile.length; i++)
            {
                Log.d("Files", "FileName:" + listFile[i].getName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, FilePathStrings);
            lvInternalStorage.setAdapter(adapter);

        }catch(NullPointerException e){
            Log.e(TAG, "checkInternalStorage: NULLPOINTEREXCEPTION " + e.getMessage() );
        }
    }

    private void checkFilePermissions() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            int permissionCheck = this.checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE");

            permissionCheck += this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
            if (permissionCheck != 0) {

                this.requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1001); //Any number
            }
        }else{
            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }


    private void updateTimetable(XSSFWorkbook wb) {

        day = new HashMap<String, String>();
        hour = new HashMap<String, String>();
        // Adding values to HashMap as ("keys", "values")
        day.put("1", "Mon");
        day.put("2", "Tue");
        day.put("3", "Wed");
        day.put("4", "Thu");
        day.put("5", "Fri");

        hour.put("1", "1");
        hour.put("2", "2");
        hour.put("3", "3");
        hour.put("4", "4");
        hour.put("5", "5");
        hour.put("6", "6");
        hour.put("7", "Ext");



        XSSFSheet sheet=wb.getSheetAt(0);
        int rowStart=sheet.getFirstRowNum()+4;
        int rowEnd=sheet.getLastRowNum();


        final int[] counter = {0};



        ArrayList<LectureSlot> slotArray = new ArrayList<>();

        //System.out.println(rowStart+" "+rowEnd);
        Log.d(TAG,"Row Start is "+ rowStart+" and RoW END"+rowEnd);

        for(int i=rowStart;i<=rowEnd;i++) {

            XSSFRow row=sheet.getRow(i);
            //System.out.println((row.getFirstCellNum()+1)+" "+(row.getLastCellNum()-1));
            Log.d(TAG,"column start "+(row.getFirstCellNum()+1)+ "column end is "+(row.getLastCellNum()-1));

            for(int j=row.getFirstCellNum()+1;j<=(row.getLastCellNum()-1);j++) {

                int gridSize=1,freecheck=0;
                String tempvar;


                LectureSlot tempSlot;

                XSSFCell cell= row.getCell(j);
                String a = Integer.toString(i-rowStart+1);
                String b = Integer.toString(j);

                //push with custom id
                String collectionName =a+" row "+b+" column "+ day.get(a) + " " + hour.get(b);
                Log.d(TAG,"the value of i and j are respectively "+i+" "+j+" cell.getStringCellValue "+cell.getStringCellValue());
                XSSFColor bgColor = cell.getCellStyle().getFillForegroundColorColor();
                Log.d(TAG,"BEFORE"+cell.getStringCellValue()+"AFTER"+i+j);
                Log.d(TAG,"FIRST CHECK  at "+i+" and "+j+" is ");


                if(bgColor== null || bgColor.getARGBHex().equals("FFFFFFFF") || bgColor.getARGBHex().equals("#FFFFFFFF"))
                {
                    if (cell.getStringCellValue().equalsIgnoreCase("") ||cell.getStringCellValue().equalsIgnoreCase("")|| cell.getStringCellValue().equals(NULL))
                    {
                        // This cell is for free period
                        freecheck=1;
                        Log.d(TAG," free period at "+i+j);
                        tempSlot=new LectureSlot("","","","",""," FREE ","","","","","","","#FFFFFF");

                    }
                    else {
                        // normal hour
                        Log.d(TAG, "this has no background colour"+i+j);
                        tempSlot = split(cell.getStringCellValue());
                        tempSlot.setColorOfTheSlot("#FFFFFF");
                    }
                    //just for avoiding an error. it wont be used for this case
                    tempvar=tempSlot.getCourseName();
                }
                else {
                    String str =bgColor.getARGBHex();
                    // this is for one cell occupying colored things like TAG


                    tempSlot=split(cell.getStringCellValue());
                    tempvar=tempSlot.getCourseName();
                    tempSlot.setColorOfTheSlot("#FFFFFF");
//                        if(cell.getStringCellValue() == "" || cell.getStringCellValue() ==" ")
//                            tempSlot=new LectureSlot("","","","",""," FREE ","","","","","","");
//                        else
//                            tempSlot=split(cell.getStringCellValue());
//
                    Log.d(TAG,"the back ground colour at "+i+" and "+j+" is "+str);
                    if(bgColor.getARGBHex().equals("FFC0504D"))
                    {
                        //this is for merged cells
                        gridSize=2;
                        tempSlot.setColorOfTheSlot("#FFFF99");

                        //                       tempSlot=new LectureSlot("","","","","","","","","","","","");
                    }

                }
                if(cell.getStringCellValue().split("\\r?\\n").length==1 && freecheck==0){

                    tempSlot.setDepartment(tempSlot.getCourseName());
                    tempSlot.setCourseName("");
                }
                //Log.d(TAG,"-----------------------------------------------------------");
                slotArray.add(tempSlot);



                Log.d(TAG, "document id of the lecture slot is "+day.get(a) + hour.get(b));
                db = FirebaseFirestore.getInstance();
                mAuth = FirebaseAuth.getInstance();
                String userID = mAuth.getCurrentUser().getUid();
                tempSlot.setDay(day.get(a));
                tempSlot.setHour(hour.get(b));


                //updating to database
                db.collection("Faculty").document(userID).collection("TimeTable").document(collectionName)
                        .set(tempSlot)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "Time Table DocumentSnapshot written with ID: "+collectionName);
                                counter[0] = counter[0] +1;
                                Log.d(TAG,"the value of counter[0] is "+ String.valueOf(counter[0]));

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error writing document", e);
                            }
                        });

                if(gridSize==2)
                {
                    j++;
                    String a1 = Integer.toString(i-rowStart+1);
                    String b1 = Integer.toString(j);

                    //push with custom id
                    String collectionName1 =a1+" row "+b1+" column "+ day.get(a1) + " " + hour.get(b1);
                    //updating to database
                    db.collection("Faculty").document(userID).collection("TimeTable").document(collectionName1)
                            .set(new LectureSlot(day.get(a1),hour.get(b1),"","","",tempvar,"","","","","","","#FFFF99"))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "Time Table DocumentSnapshot written with ID: "+collectionName);
                                    counter[0] = counter[0] +1;
                                    Log.d(TAG,"the value of counter[0] is "+ String.valueOf(counter[0]));

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error writing document", e);
                                }
                            });



                }

            }
            Log.d(TAG,"-------------------------------------------");

        }
        Intent i=new Intent(this,Portal.class);
        startActivity(i);
        toastMessage("Time Table added to your account");



    }

    private LectureSlot split(String str) {

        int flag=0;
        String arr[]=str.split("\\r?\\n");
        if(arr.length==1) {
            slot.setCourseName(arr[0]);
            slot.setDepartment("");
            slot.setAssistingFaculty("");
            slot.setBlock("");
            slot.setCourseCode("");
            slot.setDay("");
            slot.setDegree("");
            slot.setHour("");
            slot.setFloor("");
            slot.setSemester("");
            slot.setRoomNo("");
            slot.setSection("");
        }
        else
        {
            if(arr.length<5)
                slot.setAssistingFaculty("");
            if(arr.length==3){
                slot.setCourseCode("");
                flag=1;
            }

            for(int i=0;i<arr.length;i++) {
                //System.out.println((i+1)+arr[i]+" ");
                Log.d(TAG,"the value of (i+1) and arr[i] is"+(i+1)+" "+arr[i]);

                switch(flag) {

                    case 0:
                        slot.setCourseCode(arr[i]);
                        //System.out.println(flag+arr[i]+" ");
                        Log.d(TAG,"the value of flag and arr[i]"+flag+arr[i]);
                        flag++;
                        break;

                    case 1: slot.setCourseName(arr[i]);
                        //System.out.println(flag+arr[i]+" ");
                        Log.d(TAG,"the value of flag and arr[i]"+flag+arr[i]);
                        flag++;
                        break;

                    case 2: String temp1[]=arr[i].split("-");
                        for(int v=0;v<temp1.length;v++) {
                            //System.out.println(temp1[v]+" "+temp1.length);
                            Log.d(TAG,"the value of temp1[v] and temp1.length "+temp1[v]+" "+temp1.length);
                        }
                        slot.setDegree(temp1[0]);
                        slot.setDepartment(temp1[1]);
                        slot.setSemester(temp1[2]);
                        if(temp1.length==3)
                            slot.setSection("");
                        else
                            slot.setSection(temp1[3]);
                        flag++;
                        break;

                    case 3: String temp2[]=arr[i].split("-");
                        for(int v=0;v<temp2.length;v++) {
                            //System.out.println(temp1[v]+" "+temp1.length);
                            Log.d(TAG,"the value of temp1[v] and temp1.length "+temp2[v]+" "+temp2.length);
                        }

                        slot.setBlock(temp2[0]);
                        slot.setFloor(temp2[1]);
                        slot.setRoomNo(temp2[2]);

                        flag++;
                        break;

                    case 4:

                        Log.d(TAG,"the value of flag and arr[i]"+flag+arr[i]);
                        slot.setAssistingFaculty(arr[i]);
                        break;



                }
            }
        }
        return slot;

    }
}
