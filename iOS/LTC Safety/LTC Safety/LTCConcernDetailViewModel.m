//
//  LTCConcernDetailViewModel.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-04.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import "LTCConcernDetailViewModel.h"
#import "LTCClientApi.h"

// The prompt keys that map to a string in the Localizable.strings
NSString *const LTCDetailContactInfoTitle               = @"CONTACT_INFO_TITLE";            //Contact Information
NSString *const LTCDetailConcernInfoTitle               = @"DETAIL_CONCERN_INFO_TITLE";     //Concern Information
NSString *const LTCDetailActionsTakenTitle              = @"DETAIL_ACTIONS_TAKEN_TITLE";    //Actions Taken
NSString *const LTCDetailConcernStatusLog               = @"DETAIL_STATUS_LOG";         //Date submitted
NSString *const LTCSubmissionStatus                     = @"DETAIL_CONCERN_STATUS";         //Status
NSString *const LTCRetractConcernPrompt                 = @"RETRACT_CONCERN_PROMPT";         //Retract



// The row desriptors for unique identification
NSString *const LTCDetailDescriptorRetractConcern       = @"RETRACT_CONCERN";
NSString *const LTCDetailDescriptorReporterName         = @"REPORTER_NAME";
NSString *const LTCDetailDescriptorPhoneNumber          = @"PHONE_NUMBER";
NSString *const LTCDetailDescriptorEmail                = @"EMAIL";
NSString *const LTCDetailDescriptorConcernNature        = @"CONCERN_NATURE";
NSString *const LTCDetailDescriptorFacilityName         = @"FACILITY_NAME";
NSString *const LTCDetailDescriptorRoomNumber           = @"ROOM_NUMBER";
NSString *const LTCDetailDescriptorActionsTaken         = @"ACTIONS_TAKEN";
NSString *const LTCDetailDescriptorStatus               = @"CONCERN_STATUS";





@interface LTCConcernDetailViewModel ()
@property (nonatomic, strong) LTCClientApi *clientApi;
@property (readwrite, nonatomic, strong) LTCConcern *concern;
@end

@implementation LTCConcernDetailViewModel








/**
 Get the retract row descriptor's callback action
 
 @pre The model must have a with the LTCDescriptorRetractConcern tag
 */
- (SEL)retractCallback {

    // Get the retract row discriptor's formSelector
    XLFormRowDescriptor *descriptor =  [self formRowWithTag:LTCDetailDescriptorRetractConcern];
    NSAssert1(descriptor != nil, @"Unable to find descriptor with tag %@", LTCDetailDescriptorRetractConcern);
    
    return descriptor.action.formSelector;
}


/**
 Set the retract row descriptor's callback action
 
 @pre The model must have a with the LTCDescriptorRetractConcern tag
 @param retractCallback The callback that is called when the retract concern row is tapped
 */

- (void)setRetractCallback:(SEL)retractCallback {

    // Set the retract row discriptor's formSelector
    XLFormRowDescriptor *descriptor =  [self formRowWithTag:LTCDetailDescriptorRetractConcern];
    //NSAssert1(descriptor != nil, @"Unable to find descriptor with tag %@", LTCDetailDescriptorRetractConcern);
    
    if(![self.concern.statuses.lastObject.concernType isEqualToString: @"RETRACTED"]){
       descriptor.action.formSelector = retractCallback;
    }
}

- (instancetype)initWithConcern:(LTCConcern *)concern {
    
    NSAssert(concern != nil, @"Attempted to initialize the detail concern view model with a nil concern.");
    if (self = [super init]) {
        
        self.clientApi = [[LTCClientApi alloc] init];
        self.concern = concern;
        XLFormSectionDescriptor *section;
        XLFormRowDescriptor *row;
        
        // Sets up the form reporter information section
        
        // Sets up the concern information section
        
        section = [XLFormSectionDescriptor formSection];
        section.title = NSLocalizedString(LTCDetailContactInfoTitle, nil);
        [self addFormSection:section];
        row = [XLFormRowDescriptor formRowDescriptorWithTag:LTCDetailDescriptorReporterName rowType:XLFormRowDescriptorTypeInfo title:@"Name"];
        row.value = concern.reporter.name;
        [section addFormRow:row];
        if(concern.reporter.email != nil){
            row = [XLFormRowDescriptor formRowDescriptorWithTag:LTCDetailDescriptorEmail rowType:XLFormRowDescriptorTypeInfo title:@"Email"];
            row.value = concern.reporter.email;
            [section addFormRow:row];
        }
        if(concern.reporter.phoneNumber != nil){
            row = [XLFormRowDescriptor formRowDescriptorWithTag:LTCDetailDescriptorPhoneNumber rowType:XLFormRowDescriptorTypeInfo title:@"Phone Number"];
            row.value = concern.reporter.phoneNumber;
            [section addFormRow:row];
        }
        
        section = [XLFormSectionDescriptor formSection];
        
        section.title = NSLocalizedString(LTCDetailConcernInfoTitle, nil);
        [self addFormSection:section];
        row = [XLFormRowDescriptor formRowDescriptorWithTag:LTCDetailDescriptorConcernNature rowType:XLFormRowDescriptorTypeInfo title:@"Concern Nature"];
        row.value = concern.concernNature;
        [section addFormRow:row];
        row = [XLFormRowDescriptor formRowDescriptorWithTag:LTCDetailDescriptorFacilityName rowType:XLFormRowDescriptorTypeInfo title:@"Facility Name"];
        row.value = concern.location.facilityName;
        [section addFormRow:row];
        row = [XLFormRowDescriptor formRowDescriptorWithTag:LTCDetailDescriptorRoomNumber rowType:XLFormRowDescriptorTypeInfo title:@"Room"];
        row.value = concern.location.roomName;
        [section addFormRow:row];
        row = [XLFormRowDescriptor formRowDescriptorWithTag:LTCDetailDescriptorActionsTaken rowType:XLFormRowDescriptorTypeInfo title:@"Actions Taken"];
        row.value = concern.actionsTaken;
        [section addFormRow:row];
        section = [XLFormSectionDescriptor formSection];
        
        section.title = NSLocalizedString(LTCDetailConcernStatusLog, nil);
        [self addFormSection:section];
        NSString *dateString;
        for (LTCConcernStatus* status in concern.statuses){
            dateString = [NSDateFormatter localizedStringFromDate: status.creationDate
                                                        dateStyle:NSDateFormatterMediumStyle
                                                        timeStyle:NSDateFormatterShortStyle];
            row = [XLFormRowDescriptor formRowDescriptorWithTag:LTCDetailDescriptorStatus rowType:XLFormRowDescriptorTypeInfo title:status.concernType];
            row.value = dateString;
            [section addFormRow:row];

        }
        
        section = [XLFormSectionDescriptor formSection];
        [self addFormSection:section];
        
        // Sets up the actions retract button section
        
        section = [XLFormSectionDescriptor formSection];
        [self addFormSection:section];
        row = [XLFormRowDescriptor formRowDescriptorWithTag:LTCDetailDescriptorRetractConcern rowType:XLFormRowDescriptorTypeButton title:NSLocalizedString(LTCRetractConcernPrompt, nil)];

        if(![concern.statuses.lastObject.concernType isEqualToString: @"RETRACTED"]){
            [section addFormRow: row];

        }
        

    }
    NSAssert1(self != nil, @"Failed to initialize %@", self.class);
    NSAssert(self.clientApi != nil, @"Client API was nil after the detail concern view model initializer finished.");
    
    return self;
}

@end
