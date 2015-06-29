    package agsf.horizontalscroller;

    import android.app.Activity;
    import android.graphics.Bitmap;
    import android.graphics.BitmapFactory;
    import android.os.Bundle;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.MotionEvent;
    import android.view.View;
    import android.widget.ImageView;
    import android.widget.TextView;

    /**
     * Antwan Ibraheem - 2015
     * This Activity is holds an example of how to implement a horizentall scroller
     * based on an image. It was adapted from my answer to a question on StackOverflow regarding
     * the topic and i decided to make it into an example. I'll turn this into a library soon.
     */
    public class MainActivity extends Activity implements View.OnTouchListener {

        // width and height of the full scroller
        static final int  SCROLLER_WIDTH = 180*9;
        static final int SCROLLER_HEIGHT = 180*3;

        // image view to show what part of the scroller is being shown
        ImageView imageView;

        // a bitmap to hold the current part of scroller being shown
        Bitmap scrollerImage;

        // textView to hold the value of the scroller
        TextView textView;

        // where to start the new scroller that is shwon
        int scrollerStartPos = 0;

        // what scroller value is being shown
        int scrollerValue = 1;

        // holds the location of the last user touch on the X axis
        float previousX = 0;

        /**
         * On create method called when the activity is first created, it gets initializes
         * the UI views needed and sets up the necessary listeners, in addition to getting the
         * original full scroller image and rendering it at its initial state
         *
         * @param savedInstanceState Previously saved instance state if it exists.
         */
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            // get the image view we want
            imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setOnTouchListener(this);
            textView = (TextView) findViewById(R.id.textView);
            scrollerImage = BitmapFactory.decodeResource(getResources(), R.drawable.img1);
            RenderImage();
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }

        /**
         * Renders the part of the bitmap that is supposed to be shown to the user and updates the
         * scroller value.
         */
        private void RenderImage()
        {
            // create the cropped version we want to show and render it
            Bitmap renderImg = Bitmap.createBitmap(scrollerImage, scrollerStartPos,0,SCROLLER_WIDTH,
                                                    SCROLLER_HEIGHT);
            imageView.setImageBitmap(renderImg);

            // update scroller value
            if (scrollerStartPos < 180*2)
                scrollerValue = 1;
            else if (scrollerStartPos < 180*4)
                scrollerValue = 2;
            else
                scrollerValue = 3;

            // update the value shown at the testView
            textView.setText("The scroller value is: " + scrollerValue);
        }

        /**
         * Handles the user's touch on the scroller, calculates the change and renders accordingly.
         */
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // if the user has placed his hand down on the screen, get the item, X position
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                previousX = (int) event.getRawX();
            }
            // the user's finger moved after it was down, get current x and calculate the change
            else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                float x = event.getRawX();

                // move start position accordingly to the change in x position
                scrollerStartPos = (int) (previousX - x);

                // make sure scroller is within range
                scrollerStartPos = Math.max(scrollerStartPos, 0);
                scrollerStartPos = Math.min(scrollerStartPos, scrollerImage.getWidth()
                                            - SCROLLER_WIDTH);
                previousX = x;
                // update image
                RenderImage();
            }
            return true;
        }
    }
