/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://github.com/google/apis-client-generator/
 * (build: 2017-02-15 17:18:02 UTC)
 * on 2017-03-18 at 22:39:45 UTC 
 * Modify at your own risk.
 */

package com.appspot.ltc_safety.admin.model;

/**
 * Model definition for AccountListRequest.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the Admin API. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class AccountListRequest extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String accessToken;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String accountType;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer limit;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer offset;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getAccessToken() {
    return accessToken;
  }

  /**
   * @param accessToken accessToken or {@code null} for none
   */
  public AccountListRequest setAccessToken(java.lang.String accessToken) {
    this.accessToken = accessToken;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getAccountType() {
    return accountType;
  }

  /**
   * @param accountType accountType or {@code null} for none
   */
  public AccountListRequest setAccountType(java.lang.String accountType) {
    this.accountType = accountType;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getLimit() {
    return limit;
  }

  /**
   * @param limit limit or {@code null} for none
   */
  public AccountListRequest setLimit(java.lang.Integer limit) {
    this.limit = limit;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getOffset() {
    return offset;
  }

  /**
   * @param offset offset or {@code null} for none
   */
  public AccountListRequest setOffset(java.lang.Integer offset) {
    this.offset = offset;
    return this;
  }

  @Override
  public AccountListRequest set(String fieldName, Object value) {
    return (AccountListRequest) super.set(fieldName, value);
  }

  @Override
  public AccountListRequest clone() {
    return (AccountListRequest) super.clone();
  }

}
