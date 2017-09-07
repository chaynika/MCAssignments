package com.healthmonitor.khann.healthmonitor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.Viewport;
import java.util.Random;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.util.Log;
import android.os.SystemClock;



public class MainActivity extends AppCompatActivity implements OnClickListener {
    // Declare all the private variables for this class
    private LineGraphSeries mSeries1 = new LineGraphSeries<>();

    Random rand = new Random();
    int x_value = 0;
    Thread thread = new Thread();
    Button runButton, stopButton;
    GraphView graphView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {




        // All initializations should go here
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        runButton = (Button) findViewById(R.id.run);
        runButton.setOnClickListener(this);
        stopButton = (Button) findViewById(R.id.stop);
        stopButton.setOnClickListener(this);
        graphView = (GraphView) findViewById(R.id.graph);
//        graphView.addSeries(mSeries1);

        Viewport viewport = graphView.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setMinY(0);
        viewport.setMaxY(2000);
        viewport.setScrollable(true);
        viewport.setBackgroundColor(-16777216);
        viewport.setDrawBorder(true);
        viewport.setXAxisBoundsManual(true);
        viewport.setMinX(0);
        viewport.setMaxX(40);
        viewport.setScalable(true);
    }


        @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.run:
                Log.d("MR.bool", "Run was clicked ");
                runButton.setEnabled(false);
                stopButton.setEnabled(true);
                graphView.addSeries(mSeries1);

                thread = new Thread(new Runnable() {
                    @Override

                    public void run() {

                        // we add 100 new entries
                        for (int i = 0; i < 5000; i++) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    addEntry();
                                }
                            });

                            // sleep to slow down the add of entries
                            try {
                               Thread.sleep(500);

                            } catch (InterruptedException e) {

                                // manage error ...
                            }
                        }
                    }
                });
                thread.start();
                break;
            case R.id.stop:
                // TODO Need to write function for stop by adding wait() to the thread

                // Pause thread


                Log.d("MR.bool", "Stop was clicked ");
                GraphView graph = (GraphView) findViewById(R.id.graph);
                graph.removeAllSeries();
                runButton.setEnabled(true);
                stopButton.setEnabled(false);
                break;
        }
    }


    private void addEntry() {
        float y = rand.nextFloat() * (2000 - 20) + 20;
        mSeries1.appendData(new DataPoint(x_value, y), true, 500);
        x_value += 1;
    }
}