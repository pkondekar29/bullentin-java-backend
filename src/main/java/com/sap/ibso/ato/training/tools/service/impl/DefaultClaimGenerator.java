package com.sap.ibso.ato.training.tools.service.impl;

import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.sap.ibso.ato.training.tools.service.ClaimGenerator;

@Service
@Primary
public class DefaultClaimGenerator implements ClaimGenerator {
	
private static final String input = "Q2h1Y2sgTm9ycmlzIHRocmV3IGEgZ3JlbmFkZSBhbmQga2lsbGVkIDUwIHBlb3BsZSwgdGhlbiBpdCBleHBsb2RlZC4KQ2h1Y2sgTm9ycmlzIGNhbiBraWxsIHR3byBzdG9uZXMgd2l0aCBvbmUgYmlyZC4KQ2h1Y2sgTm9ycmlzIGNhbiBwaWNrIG9yYW5nZXMgZnJvbSBhbiBhcHBsZSB0cmVlIGFuZCBtYWtlIHRoZSBiZXN0IGxlbW9uYWRlIHlvdSd2ZSBldmVyIHRhc3RlZC4KQ2h1Y2sgTm9ycmlzIGNhbiBoZWFyIHNpZ24gbGFuZ3VhZ2UuCkNodWNrIE5vcnJpcyBjb3VudGVkIHRvIGluZmluaXR5LiBUd2ljZS4KSXQgaXMgY29uc2lkZXJlZCBhIGdyZWF0IGFjY29tcGxpc2htZW50IHRvIGdvIGRvd24gTmlhZ2FyYSBGYWxscyBpbiBhIHdvb2RlbiBiYXJyZWwuIENodWNrIE5vcnJpcyBjYW4gZ28gdXAgTmlhZ2FyYSBGYWxscyBpbiBhIGNhcmRib2FyZCBib3guCkNodWNrIE5vcnJpcyBjYW4gYnVpbGQgYSBzbm93bWFuIG91dCBvZiByYWluLgpDaHVjayBOb3JyaXMgY2FuIHN0cmFuZ2xlIHlvdSB3aXRoIGEgY29yZGxlc3MgcGhvbmUuCkNodWNrIE5vcnJpcyB3YXMgb25jZSBjaGFyZ2VkIHdpdGggdGhyZWUgYXR0ZW1wdGVkIG11cmRlcnMgaW4gQm91bGRlciBDb3VudHksIGJ1dCB0aGUgSnVkZ2UgcXVpY2tseSBkcm9wcGVkIHRoZSBjaGFyZ2VzIGJlY2F1c2UgQ2h1Y2sgTm9ycmlzIGRvZXMgbm90ICJhdHRlbXB0IiBtdXJkZXIuCkNodWNrIE5vcnJpcyB3YXMgb25jZSBvbiBDZWxlYnJpdHkgV2hlZWwgb2YgRm9ydHVuZSBhbmQgd2FzIHRoZSBmaXJzdCB0byBzcGluLiBUaGUgbmV4dCAyOSBtaW51dGVzIG9mIHRoZSBzaG93IGNvbnNpc3RlZCBvZiBldmVyeW9uZSBzdGFuZGluZyBhcm91bmQgYXdrd2FyZGx5LCB3YWl0aW5nIGZvciB0aGUgd2hlZWwgdG8gc3RvcC4KR2lyYWZmZXMgd2VyZSBjcmVhdGVkIHdoZW4gQ2h1Y2sgTm9ycmlzIHVwcGVyY3V0dGVkIGEgaG9yc2UuCk9uY2UgYSBjb2JyYSBiaXQgQ2h1Y2sgTm9ycmlzJyBsZWcuIEFmdGVyIGZpdmUgZGF5cyBvZiBleGNydWNpYXRpbmcgcGFpbiwgdGhlIGNvYnJhIGRpZWQuCldoZW4gdGhlIEJvb2dleW1hbiBnb2VzIHRvIHNsZWVwIGV2ZXJ5IG5pZ2h0IGhlIGNoZWNrcyBoaXMgY2xvc2V0IGZvciBDaHVjayBOb3JyaXMuCkNodWNrIE5vcnJpcyBkb2Vzbid0IGNoZWF0IGRlYXRoLiBIZSB3aW5zIGZhaXIgYW5kIHNxdWFyZS4KTGVhZGluZyBoYW5kIHNhbml0aXplcnMgY2xhaW0gdGhleSBjYW4ga2lsbCA5OS45IHBlcmNlbnQgb2YgZ2VybXMuIENodWNrIE5vcnJpcyBjYW4ga2lsbCAxMDAgcGVyY2VudCBvZiB3aGF0ZXZlciB0aGUgaGVsbCBoZSB3YW50cy4KQ2h1Y2sgcmVmZXJzIHRvIGhpbXNlbGYgaW4gdGhlIGZvdXJ0aCBwZXJzb24uCldoZW4gQ2h1Y2sgTm9ycmlzIHdhcyBpbiBtaWRkbGUgc2Nob29sLCBoaXMgRW5nbGlzaCB0ZWFjaGVyIGFzc2lnbmVkIGFuIGVzc2F5OiAiV2hhdCBpcyBjb3VyYWdlPyIgSGUgcmVjZWl2ZWQgYW4gQSsgZm9yIHR1cm5pbmcgaW4gYSBibGFuayBwYWdlIHdpdGggb25seSBoaXMgbmFtZSBhdCB0aGUgdG9wLgpUaGVyZSBpcyBubyB0aGVvcnkgb2YgZXZvbHV0aW9uLCBqdXN0IGEgbGlzdCBvZiBjcmVhdHVyZXMgQ2h1Y2sgTm9ycmlzIGFsbG93cyB0byBsaXZlLgpDaHVjayBOb3JyaXMnIGNhbGVuZGFyIGdvZXMgc3RyYWlnaHQgZnJvbSBNYXJjaCAzMXN0IHRvIEFwcmlsIDJuZC4gTm8gb25lIGZvb2xzIENodWNrIE5vcnJpcy4KV2hlbiBhIHpvbWJpZSBhcG9jYWx5cHNlIHN0YXJ0cywgQ2h1Y2sgTm9ycmlzIGRvZXNuJ3QgdHJ5IHRvIHN1cnZpdmUuIFRoZSB6b21iaWVzIGRvLgpDaHVjayBOb3JyaXMgd2lsbCBuZXZlciBoYXZlIGEgaGVhcnQgYXR0YWNrLi4uIGV2ZW4gYSBoZWFydCBpc24ndCBmb29saXNoIGVub3VnaCB0byBhdHRhY2sgQ2h1Y2sgTm9ycmlzLgpDaHVjayBOb3JyaXMgY2FuIGtpbGwgeW91ciBpbWFnaW5hcnkgZnJpZW5kcy4KQ2h1Y2sgY2FuIHNldCBhbnRzIG9uIGZpcmUgd2l0aCBhIG1hZ25pZnlpbmcgZ2xhc3MuIEF0IG5pZ2h0LgpDaHVjayBOb3JyaXMncyBkYXVnaHRlciBsb3N0IGhlciB2aXJnaW5pdHksIGhlIGdvdCBpdCBiYWNrLgpDaHVjayBOb3JyaXMgb25jZSB3ZW50IHRvIG1hcnMuIFRoYXRzIHdoeSB0aGVyZSBhcmUgbm8gc2lnbnMgb2YgbGlmZS4KQ2h1Y2sgTm9ycmlzIG1ha2VzIG9uaW9ucyBjcnkuCkNodWNrIE5vcnJpcyB0ZWxscyBTaW1vbiB3aGF0IHRvIGRvLgpDaHVjayBOb3JyaXMga25vd3MgVmljdG9yaWEncyBzZWNyZXQuCkNodWNrIE5vcnJpcyBiZWF0IHRoZSBzdW4gaW4gYSBzdGFyaW5nIGNvbnRlc3QuClRoZSByZWFzb24gdGhlIEhvbHkgR3JhaWwgaGFzIG5ldmVyIGJlZW4gcmVjb3ZlcmVkIGlzIGJlY2F1c2Ugbm9ib2R5IGlzIGJyYXZlIGVub3VnaCB0byBhc2sgQ2h1Y2sgTm9ycmlzIHRvIGdpdmUgdXAgaGlzIGZhdm91cml0ZSBjb2ZmZWUgbXVnLgpXaGVuIEJydWNlIEJhbm5lciBnZXRzIG1hZCBoZSB0dXJucyBpbnRvIHRoZSBIdWxrLiBXaGVuIHRoZSBIdWxrIGdldHMgbWFkIGhlIHR1cm5zIGludG8gQ2h1Y2sgTm9ycmlzLiBXaGVuIENodWNrIE5vcnJpcyBnZXRzIG1hZCwgcnVuLgpDaHVjayBOb3JyaXMgaXMgdGhlIHJlYXNvbiBXYWxkbyBpcyBoaWRpbmcuCkNodWNrIE5vcnJpcydzIEJsb29kIFR5cGUgaXMgQUstNDcuCkNodWNrIE5vcnJpcyBpcyB0aGUgb25seSBwZXJzb24gdGhhdCBjYW4gcHVuY2ggYSBjeWNsb3BzIGJldHdlZW4gdGhlIGV5ZS4KQnJldHQgRmF2cmUgY2FuIHRocm93IGEgZm9vdGJhbGwgb3ZlciA1MCB5YXJkcy4gQ2h1Y2sgTm9ycmlzIGNhbiB0aHJvdyBCcmV0dCBGYXZyZSBldmVuIGZ1cnRoZXIuCldoZW4gQ2h1Y2sgTm9ycmlzIGVudGVycyBhIHJvb20sIGhlIGRvZXNuJ3QgdHVybiB0aGUgbGlnaHRzIG9uLCBoZSB0dXJucyB0aGUgZGFyayBvZmYuCk0uQy4gSGFtbWVyIGxlYXJuZWQgdGhlIGhhcmQgd2F5IHRoYXQgQ2h1Y2sgTm9ycmlzIGNhbiB0b3VjaCB0aGlzLgpDaHVjayBOb3JyaXMgcGxheXMgUnVzc2lhbiByb3VsZXR0ZSB3aXRoIGEgZnVsbHkgbG9hZGVkIHJldm9sdmVyLi4uIGFuZCB3aW5zLgpCaWxsIEdhdGVzIGxpdmVzIGluIGNvbnN0YW50IGZlYXIgdGhhdCBDaHVjayBOb3JyaXMnIFBDIHdpbGwgY3Jhc2guCkRlYXRoIG9uY2UgaGFkIGEgbmVhci1DaHVjay1Ob3JyaXMgZXhwZXJpZW5jZS4KQ2h1Y2sgTm9ycmlzIGlzIHRoZSBvbmx5IHBlcnNvbiBvbiB0aGUgcGxhbmV0IHRoYXQgY2FuIGtpY2sgeW91IGluIHRoZSBiYWNrIG9mIHRoZSBmYWNlLgpDaHVjayBOb3JyaXMnIGRvZyBpcyB0cmFpbmVkIHRvIHBpY2sgdXAgaGlzIG93biBwb29wIGJlY2F1c2UgQ2h1Y2sgTm9ycmlzIHdpbGwgbm90IHRha2Ugc2hpdCBmcm9tIGFueW9uZS4KV2hlbiBDaHVjayBOb3JyaXMgZ2l2ZXMgeW91IHRoZSBmaW5nZXIsIGhlJ3MgdGVsbGluZyB5b3UgaG93IG1hbnkgc2Vjb25kcyB5b3UgaGF2ZSBsZWZ0IHRvIGxpdmUuCklmIGl0IGxvb2tzIGxpa2UgY2hpY2tlbiwgdGFzdGVzIGxpa2UgY2hpY2tlbiwgYW5kIGZlZWxzIGxpa2UgY2hpY2tlbiBidXQgQ2h1Y2sgTm9ycmlzIHNheXMgaXRzIGJlZWYsIHRoZW4gaXQncyBiZWVmLgpDaHVjayBOb3JyaXMgY2FuIHNwZWFrIGJyYWlsbGUuCkNodWNrIE5vcnJpcyBwdXRzIHRoZSAibGF1Z2h0ZXIiIGluICJtYW5zbGF1Z2h0ZXIiLgpDaHVjayBOb3JyaXMgY2FuIGRvIGEgd2hlZWxpZSBvbiBhIHVuaWN5Y2xlLgpDaHVjayBhY3R1YWxseSBkaWVkIGZvdXIgeWVhcnMgYWdvLCBidXQgdGhlIEdyaW0gUmVhcGVyIGNhbid0IGdldCB1cCB0aGUgY291cmFnZSB0byB0ZWxsIGhpbS4KQ2h1Y2sgTm9ycmlzIGRvZXNuJ3QgaGF2ZSBnb29kIGFpbS4gSGlzIGJ1bGxldHMganVzdCBrbm93IGJldHRlciB0aGFuIHRvIG1pc3MuCkNodWNrIE5vcnJpcyBiZWF0IEhhbG8gMSwgMiwgYW5kIDMgb24gTGVnZW5kYXJ5IHdpdGggYSBicm9rZW4gR3VpdGFyIEhlcm8gY29udHJvbGxlci4KQ2h1Y2sgTm9ycmlzJ3MgY29tcHV0ZXIgaGFzIG5vICJiYWNrc3BhY2UiIGJ1dHRvbiwgQ2h1Y2sgTm9ycmlzIGRvZXNuJ3QgbWFrZSBtaXN0YWtlcy4KVGhlIHJlYWwgcmVhc29uIEhpdGxlciBraWxsZWQgaGltc2VsZiBpcyBiZWNhdXNlIGhlIGZvdW5kIG91dCB0aGF0IENodWNrIE5vcnJpcyBpcyBKZXdpc2guCkNodWNrIE5vcnJpcyBzbGVlcHMgd2l0aCBhIHBpbGxvdyB1bmRlciBoaXMgZ3VuLgpDaHVjayBOb3JyaXMgZG9lc24ndCBwbGF5ICJoaWRlLWFuZC1zZWVrLiIgSGUgcGxheXMgImhpZGUtYW5kLXByYXktSS1kb24ndC1maW5kLXlvdS4iCkphY2sgd2FzIG5pbWJsZSwgSmFjayB3YXMgcXVpY2ssIGJ1dCBKYWNrIHN0aWxsIGNvdWxkbid0IGRvZGdlIENodWNrIE5vcnJpcycgcm91bmRob3VzZSBraWNrLgpDaHVjayBOb3JyaXMgaXMgdGhlIG9ubHkgbWFuIHRvIGV2ZXIgZGVmZWF0IGEgYnJpY2sgd2FsbCBpbiBhIGdhbWUgb2YgdGVubmlzLgpDaHVjayBOb3JyaXMgY2FuIHVuc2NyYW1ibGUgYW4gZWdnLgpDaHVjayBOb3JyaXMnIHRlYXJzIGN1cmUgY2FuY2VyLiBUb28gYmFkIGhlIGhhcyBuZXZlciBjcmllZC4KQ2h1Y2sgTm9ycmlzIGhhcyBhIGRpYXJ5LiBJdCdzIGNhbGxlZCB0aGUgR3Vpbm5lc3MgQm9vayBvZiBXb3JsZCBSZWNvcmRzLgpDaHVjayBOb3JyaXMgZG9lc24ndCB3ZWFyIGEgd2F0Y2guIEhlIGRlY2lkZXMgd2hhdCB0aW1lIGl0IGlzLgpDaHVjayBOb3JyaXMgaGFzIGEgYmVhciBydWcgaW4gaGlzIHRyb3BoeSByb29tLiBJdCdzIHN0aWxsIGFsaXZlIGJ1dCBpcyBqdXN0IHRvbyBhZnJhaWQgdG8gZ2V0IHVwLgpDaHVjayBOb3JyaXMgb25jZSBib3dsZWQgYSBwZXJmZWN0IGdhbWUgd2l0aCBhIG1hcmJsZS4KQ2h1Y2sgTm9ycmlzIGNhbiBoaXQgeW91IHNvIGhhcmQgeW91ciBibG9vZCB3aWxsIGJsZWVkLgpDaHVjayBOb3JyaXMgQ0FOIGZpbmQgdGhlIGVuZCBvZiBhIGNpcmNsZS4KVGhlIHNhZGRlc3QgbW9tZW50IGZvciBhIGNoaWxkIGlzIG5vdCB3aGVuIGhlIGxlYXJucyBTYW50YSBDbGF1cyBpc24ndCByZWFsLCBpdCdzIHdoZW4gaGUgbGVhcm5zIENodWNrIE5vcnJpcyBpcy4KVGhlIG9ubHkgdGltZSBDaHVjayBOb3JyaXMgd2FzIHdyb25nIHdhcyB3aGVuIGhlIHRob3VnaHQgaGUgaGFkIG1hZGUgYSBtaXN0YWtlLgpDaHVjayBOb3JyaXMgY2FuIGRyb3duIGEgZmlzaC4KSWYgeW91IHNwZWxsIENodWNrIE5vcnJpcyB3cm9uZyBvbiBHb29nbGUgaXQgZG9lc24ndCBzYXksICJEaWQgeW91IG1lYW4gQ2h1Y2sgTm9ycmlzPyIgSXQgc2ltcGx5IHJlcGxpZXMsICJSdW4gd2hpbGUgeW91IHN0aWxsIGhhdmUgdGhlIGNoYW5jZS4iCkNodWNrIGNhbiBkaXZpZGUgYnkgemVyby4KQ2h1Y2sgTm9ycmlzIG5hcnJhdGVzIE1vcmdhbiBGcmVlbWFuJ3MgbGlmZS4KV2hlbiBDaHVjayBOb3JyaXMgd2FzIGJvcm4gaGUgZHJvdmUgaGlzIG1vbSBob21lIGZyb20gdGhlIGhvc3BpdGFsLgpJZiBoZSB3YW50ZWQgdG8sIENodWNrIE5vcnJpcyBjb3VsZCByb2IgYSBiYW5rLiBCeSBwaG9uZS4KVGhlIG9yaWdpbmFsIHRpdGxlIGZvciBBbGllbiB2cy4gUHJlZGF0b3Igd2FzIEFsaWVuIGFuZCBQcmVkYXRvciB2cyBDaHVjayBOb3JyaXMuIFRoZSBmaWxtIHdhcyBjYW5jZWxsZWQgc2hvcnRseSBhZnRlciBnb2luZyBpbnRvIHByZXByb2R1Y3Rpb24uIE5vIG9uZSB3b3VsZCBwYXkgbmluZSBkb2xsYXJzIHRvIHNlZSBhIG1vdmllIGZvdXJ0ZWVuIHNlY29uZHMgbG9uZy4KQ2h1Y2sgTm9ycmlzIHVzZWQgdG8gYmVhdCB0aGUgc2hpdCBvdXQgb2YgaGlzIHNoYWRvdyBiZWNhdXNlIGl0IHdhcyBmb2xsb3dpbmcgdG8gY2xvc2UuIEl0IG5vdyBzdGFuZHMgYSBzYWZlIDMwIGZlZXQgYmVoaW5kIGhpbS4KVGhlIHJlYXNvbiBuZXdib3JuIGJhYmllcyBjcnkgaXMgYmVjYXVzZSB0aGV5IGtub3cgdGhleSBoYXZlIGp1c3QgZW50ZXJlZCBhIHdvcmxkIHdpdGggQ2h1Y2sgTm9ycmlzLgpDaHVjayBOb3JyaXMgY2FuIGRlbGV0ZSB0aGUgUmVjeWNsaW5nIEJpbi4KUG9saWNlIGxhYmVsIGFueW9uZSBhdHRhY2tpbmcgQ2h1Y2sgTm9ycmlzIGFzIGEgQ29kZSA0NS0xMS4uLiBhIHN1aWNpZGUuClNvbWUga2lkcyBwaXNzIHRoZWlyIG5hbWUgaW4gdGhlIHNub3cuIENodWNrIE5vcnJpcyBjYW4gcGlzcyBoaXMgbmFtZSBpbnRvIGNvbmNyZXRlLgpDaHVjayBOb3JyaXMgZG9lc24ndCBkaWFsIHRoZSB3cm9uZyBudW1iZXIsIHlvdSBwaWNrIHVwIHRoZSB3cm9uZyBwaG9uZS4KQ2h1Y2sgTm9ycmlzIGNhbiBjdXQgYSBrbmlmZSB3aXRoIGJ1dHRlci4KQ2h1Y2sgTm9ycmlzIGNhbiBzcGVhayBGcmVuY2guLi4gaW4gUnVzc2lhbi4KQ2h1Y2sgTm9ycmlzIGNhbiBtYWtlIGEgc2xpbmt5IGdvIHVwc3RhaXJzLgpPbiBhIGhpZ2ggc2Nob29sIG1hdGggdGVzdCwgQ2h1Y2sgTm9ycmlzIHB1dCBkb3duICJWaW9sZW5jZSIgYXMgZXZlcnkgb25lIG9mIHRoZSBhbnN3ZXJzLiBIZSBnb3QgYW4gQSsgb24gdGhlIHRlc3QgYmVjYXVzZSBDaHVjayBOb3JyaXMgc29sdmVzIGFsbCBoaXMgcHJvYmxlbXMgd2l0aCBWaW9sZW5jZS4KU3VwZXJtYW4gb3ducyBhIHBhaXIgb2YgQ2h1Y2sgTm9ycmlzIHB5amFtYXMuCkNodWNrIE5vcnJpcyBvbmx5IG5lZWRzIG9uZSBjaG9wc3RpY2suCkV2ZXJ5IENodWNrIE5vcnJpcyBqb2tlIGlzIGEgZml2ZSBzdGFyIGpva2UganVzdCBiZWNhdXNlIGl0IHNheXMgQ2h1Y2sgTm9ycmlzLgpUaGVyZSBvbmNlIHdhcyBhIHN0cmVldCBjYWxsZWQgQ2h1Y2sgTm9ycmlzLCBidXQgdGhlIG5hbWUgd2FzIGNoYW5nZWQgZm9yIHB1YmxpYyBzYWZldHkgYmVjYXVzZSBub2JvZHkgY3Jvc3NlcyBDaHVjayBOb3JyaXMgYW5kIGxpdmVzLgpDaHVjayBOb3JyaXMgb25jZSBoZWFyZCB0aGF0IG5vdGhpbmcgY2FuIGtpbGwgaGltLCBzbyBoZSB0cmFja2VkIGRvd24gbm90aGluZyBhbmQga2lsbGVkIGl0LgpDaHVjayBOb3JyaXMgaXMgc28gZmFzdCB0aGF0IHdoZW4gaGUgc3RvcHMgaGUgd2FpdHMgZm9yIGhpcyBzaGFkb3cuCkEgYnVsbGV0cHJvb2YgdmVzdCB3ZWFycyBDaHVjayBOb3JyaXMgZm9yIHByb3RlY3Rpb24uCkNodWNrIE5vcnJpcyBsb3N0IGhpcyB2aXJnaW5pdHkgYmVmb3JlIGhpcyBkYWQgZGlkLgpDaHVjayBOb3JyaXMgb25jZSBoYWQgYSBoZWFydCBhdHRhY2s7IGhpcyBoZWFydCBsb3N0LgpXaGF0IHdhcyBnb2luZyB0aHJvdWdoIHRoZSBtaW5kcyBvZiBhbGwgb2YgQ2h1Y2sgTm9ycmlzJyB2aWN0aW1zIGJlZm9yZSB0aGV5IGRpZWQ/IEhpcyBzaG9lLgpUaGUgS29vbC1haWQgbWFuIG9uY2UgYnJva2UgdGhyb3VnaCBDaHVjayBOb3JyaXMncyB3YWxsIGFuZCBzYWlkICJPaGhoIE5vLiIuCkdob3N0cyBzaXQgYXJvdW5kIGNhbXBmaXJlIGFuZCB0ZWxsIENodWNrIE5vcnJpcyBzdG9yaWVzLgpDaHVjayBOb3JyaXMgY2hlYXRlZCBvbiBoaXMgRW5nbGlzaCB0ZXN0Li4uIHdpdGggYSBjYWxjdWxhdG9yLgpDaHVjayBOb3JyaXMgY2FuIGRyYXcgYSBwZXJmZWN0IGNpcmNsZS4gV2l0aCBhIHJ1bGVyLgpDaHVjayBOb3JyaXMgY2FuIHNpdCBpbiB0aGUgY29ybmVyIG9mIGEgcm91bmQgcm9vbS4KSWYgeW91IHN3YWxsb3cgYSBxdWFydGVyIGFuZCBDaHVjayBOb3JyaXMgcm91bmQgaG91c2Uga2lja3MgeW91IGluIHRoZSBzdG9tYWNoIHlvdSB3aWxsIGNyYXAgb3V0IHR3byBkaW1lcyBhbmQgYSBuaWNrZWwuClRvdWdoIG1lbiBlYXQgbmFpbHMuIENodWNrIE5vcnJpcyBkb2VzIGFsbCBoaXMgZ3JvY2VyeSBzaG9wcGluZyBhdCBIb21lIERlcG90Lg==";
	
	public String generate() {
		byte[] bytes = Base64.decodeBase64(input);
		String claimsWithNewline = new String(bytes, Charset.defaultCharset());
		List<String> claims = new ArrayList<String>(Arrays.asList(claimsWithNewline.split("\\r?\\n")));
		
		Random rand = new SecureRandom();
		String randomClaim = claims.get(rand.nextInt(claims.size()));
		return randomClaim;
	}

}