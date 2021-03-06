/*
 *  Copyright 2013 The WebRTC Project Authors. All rights reserved.
 *
 *  Use of this source code is governed by a BSD-style license
 *  that can be found in the LICENSE file in the root of the source
 *  tree. An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */

package de.lespace.apprtc;

import org.webrtc.IceCandidate;
import org.webrtc.PeerConnection;
import org.webrtc.SessionDescription;

import java.util.List;

/**
 * AppRTCClient is the interface representing an AppRTC client.
 */
public interface AppRTCClient {

  /**
   * Struct holding the connection parameters of an AppRTC room.
   */
  public static class RoomConnectionParameters {

    public String roomUrl;
    public String from;
    public boolean initiator;
    public final boolean loopback;
    public Object to;

    public RoomConnectionParameters(

        String roomUrl,
        String from,
        boolean initiator,
        boolean loopback) {
          this.roomUrl = roomUrl;
          this.from = from;
          this.initiator = initiator;
          this.loopback = loopback;
    }

  }

  /**
   * Asynchronously connect to an AppRTC room URL using supplied connection
   * parameters. Once connection is established onConnectedToRoom()
   * callback with room parameters is invoked.
   */
  public void connectToWebsocket(RoomConnectionParameters connectionParameters);

  /**
   * Send offer SDP to the other participant.
   */
  public void call(final SessionDescription sdp);
  /**
   * Send offer SDP to the other participant.
   */
  //public void sendOfferSdp(final SessionDescription sdp);

  /**
   * Send answer SDP to the other participant.
   */
  public void sendOfferSdp(final SessionDescription sdp);

  /**
   * Send Ice candidate to the other participant.
   */
  public void sendLocalIceCandidate(final IceCandidate candidate);

  /**
   * Disconnect from room.
   */
  public void reconnect();

  /**
   * Send stop message to peer
   */
  public void sendStopToPeer();
  /**
   * Disconnect from room.
   */
  public void disconnectFromWebservice();

  /**
   * Struct holding the signaling parameters of an AppRTC room.
   */
  public static class SignalingParameters {
    public static List<PeerConnection.IceServer> iceServers;

    public SignalingParameters(
        List<PeerConnection.IceServer> iceServers) {
      this.iceServers = iceServers;
    }
  }

  /**
   * Callback interface for messages delivered on signaling channel.
   *
   * <p>Methods are guaranteed to be invoked on the UI thread of |activity|.
   */
  public static interface SignalingEvents {
    /**
     * Callback fired once the room's signaling parameters
     * SignalingParameters are extracted.
     */
    public void onConnectedToRoom(final SignalingParameters params);

    public void onUserListUpdate(String response);

    public void onIncomingCall(String from);

    public void onStartCommunication(final SessionDescription sdp);
    /**
     * Callback fired once remote SDP is received.
     */
    public void onRemoteDescription(final SessionDescription sdp);

    /**
     * Callback fired once remote Ice candidate is received.
     */
    public void onRemoteIceCandidate(final IceCandidate candidate);

    /**
     * Callback fired once channel is closed.
     */
    public void onChannelClose();

    /**
     * Callback fired once channel error happened.
     */
    public void onChannelError(final String description);


  }
}
