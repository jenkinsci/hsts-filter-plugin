/*
 * The MIT License
 * 
 * Copyright (c) 2011, Jesse Farinacci
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.jenkins.ci.plugins.hsts_filter;

import hudson.Extension;
import hudson.model.PageDecorator;
import net.sf.json.JSONObject;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

/**
 * The <a
 * href="http://wiki.jenkins-ci.org/display/JENKINS/HSTS+Filter+Plugin">HSTS
 * Filter Plugin</a> provides a very simple filter which adds a response header
 * indicating that <a
 * href="http://tools.ietf.org/html/draft-hodges-strict-transport-sec-02">HTTP
 * Strict Transport Security</a> (HSTS) response headers should be utilized.
 */
@Extension
public final class HstsFilterPageDecorator extends PageDecorator
{
  /**
   * The default value for {@link #sendHeader}.
   */
  protected static final boolean DEFAULT_SEND_HEADER         = true;

  /**
   * The default value for {@link #maxAge}.
   */
  protected static final String  DEFAULT_MAX_AGE             = "31536000";

  /**
   * The default value for {@link #includeSubDomains}.
   */
  protected static final boolean DEFAULT_INCLUDE_SUB_DOMAINS = true;

  /**
   * Whether or not to send the Strict-Transport-Security HTTP header.
   */
  private boolean                sendHeader;

  /**
   * Specifies the number of seconds, after the reception of the
   * Strict-Transport-Security HTTP Response Header, during which the UA regards
   * the host the message was received from as a Known HSTS Server.
   */
  private String                 maxAge;

  /**
   * A flag which, if present, signals to the UA that the HSTS Policy applies to
   * this HSTS Server as well as any subdomains of the server's FQDN.
   */
  private boolean                includeSubDomains;

  /**
   * Create a default HSTS Filter {@link PageDecorator}.
   */
  public HstsFilterPageDecorator()
  {
    this(DEFAULT_SEND_HEADER, DEFAULT_MAX_AGE, DEFAULT_INCLUDE_SUB_DOMAINS);
  }

  /**
   * Create a HSTS Filter {@link PageDecorator} with the specified
   * configuration.
   */
  @DataBoundConstructor
  public HstsFilterPageDecorator(final boolean sendHeader, final String maxAge,
      final boolean includeSubDomains)
  {
    super();
    load();
    this.sendHeader = sendHeader;
    this.maxAge = maxAge;
    this.includeSubDomains = includeSubDomains;
  }

  @Override
  public String getDisplayName()
  {
    return Messages.HSTS_Filter_Plugin_DisplayName();
  }

  @Override
  public boolean configure(final StaplerRequest request, final JSONObject json)
      throws FormException
  {
    request.bindJSON(this, json);
    save();
    return true;
  }

  public boolean isSendHeader()
  {
    return sendHeader;
  }

  public void setSendHeader(final boolean sendHeader)
  {
    this.sendHeader = sendHeader;
  }

  public String getMaxAge()
  {
    return maxAge;
  }

  public void setMaxAge(final String maxAge)
  {
    this.maxAge = maxAge;
  }

  public boolean isIncludeSubDomains()
  {
    return includeSubDomains;
  }

  public void setIncludeSubDomains(final boolean includeSubDomains)
  {
    this.includeSubDomains = includeSubDomains;
  }
}
