/*
 * This is free and unencumbered software released into the public domain.
 * 
 * Anyone is free to copy, modify, publish, use, compile, sell, or
 * distribute this software, either in source code form or as a compiled
 * binary, for any purpose, commercial or non-commercial, and by any
 * means.
 * 
 * In jurisdictions that recognize copyright laws, the author or authors
 * of this software dedicate any and all copyright interest in the
 * software to the public domain. We make this dedication for the benefit
 * of the public at large and to the detriment of our heirs and
 * successors. We intend this dedication to be an overt act of
 * relinquishment in perpetuity of all present and future rights to this
 * software under copyright law.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 * 
 * For more information, please refer to [http://unlicense.org]
 */

package net.lardcave.keepassnfc;

import java.io.IOException;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by wzdd on 06/07/2013.
 */
public class WriteNFCActivity extends Activity {
    private static final String LOG_TAG = "WriteNFCActivity";

    protected void onCreate(Bundle sis) {
        super.onCreate(sis);
        setContentView(R.layout.activity_write_nfc);

        setResult(0);

        Button b = (Button) findViewById(R.id.cancel_nfc_write_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View self) {
                nfc_disable();
				setResult(6);
                finish();
            }
        });
    }

    private void nfc_enable()
    {
        // Register for any NFC event (only while we're in the foreground)

        NfcAdapter adapter = NfcAdapter.getDefaultAdapter(this);
        PendingIntent pending_intent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        adapter.enableForegroundDispatch(this, pending_intent, null, null);
    }

    private void nfc_disable()
    {
        NfcAdapter adapter = NfcAdapter.getDefaultAdapter(this);

        adapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        nfc_enable();
    }

    @Override
    protected void onPause() {
        super.onPause();

        nfc_disable();
    }

    @Override
    public void onNewIntent(Intent intent)
    {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
            int success = 0;
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

            // Write the payload to the tag.
            Ndef ndef = Ndef.get(tag);
            try {
                ndef.connect();
                ndef.writeNdefMessage(WriteActivity.nfc_payload);
                ndef.close();
                success = 1;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (FormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            setResult(success);
            finish();
        }
    }
}
