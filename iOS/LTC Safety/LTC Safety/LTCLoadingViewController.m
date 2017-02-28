//
//  LTCLoadingViewController.m
//  LTC Safety
//
//  Created by Daniel Morris on 2017-02-25.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import "LTCLoadingViewController.h"

NSString *const LTCLoadingPrompt = @"LOADING_PROMPT";

@implementation LTCLoadingViewController

+ (instancetype)configure {
    
    LTCLoadingViewController *loadingMessage = [LTCLoadingViewController alertControllerWithTitle: NSLocalizedString(LTCLoadingPrompt, nil) message: nil preferredStyle: UIAlertControllerStyleAlert];
    
    UIViewController *customVC     = [[UIViewController alloc] init];
    UIActivityIndicatorView* spinner = [[UIActivityIndicatorView alloc] initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleGray];
    [spinner startAnimating];
    [customVC.view addSubview:spinner];
    [customVC.view addConstraint:[NSLayoutConstraint
                                  constraintWithItem: spinner
                                  attribute:NSLayoutAttributeCenterX
                                  relatedBy:NSLayoutRelationEqual
                                  toItem:customVC.view
                                  attribute:NSLayoutAttributeCenterX
                                  multiplier:1.0f
                                  constant:0.0f]];
    [customVC.view addConstraint:[NSLayoutConstraint
                                  constraintWithItem: spinner
                                  attribute:NSLayoutAttributeCenterY
                                  relatedBy:NSLayoutRelationEqual
                                  toItem:customVC.view
                                  attribute:NSLayoutAttributeCenterY
                                  multiplier:1.0f
                                  constant:0.0f]];
    [loadingMessage setValue:customVC forKey:@"contentViewController"];
    
    return loadingMessage;

}

@end
